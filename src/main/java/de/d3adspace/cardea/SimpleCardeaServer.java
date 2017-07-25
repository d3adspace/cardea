/*
 * Copyright (c) 2017 D3adspace
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package de.d3adspace.cardea;

import de.d3adspace.cardea.backend.BackendBalancing;
import de.d3adspace.cardea.backend.BackendBalancingFactory;
import de.d3adspace.cardea.backend.BackendManager;
import de.d3adspace.cardea.config.CardeaConfig;
import de.d3adspace.cardea.initializer.CardeaServerChannelInitializer;
import de.d3adspace.cardea.task.BackendRecoverTask;
import de.d3adspace.cardea.task.CheckDeadBackendsTask;
import de.d3adspace.cardea.utils.NettyUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.ServerChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Basic server implementation.
 *
 * @author Felix 'SasukeKawaii' Klauke
 */
public class SimpleCardeaServer implements CardeaServer {

    /**
     * The config for the server.
     */
    private final CardeaConfig config;

    /**
     * The manager for all backends.
     */
    private final BackendManager backendManager;

    /**
     * Executor for backend tasks.
     */
    private final ScheduledExecutorService executorService;

    /**
     * The logger for the server actions.
     */
    private final Logger logger;

    /**
     * Boss group for netty.
     */
    private EventLoopGroup bossGroup;

    /**
     * Worker group for netty.
     */
    private EventLoopGroup workerGroup;

    /**
     * Create a server by a config.
     *
     * @param config The config.
     */
    SimpleCardeaServer(CardeaConfig config) {
        this.config = config;

        BackendBalancing backendBalancing = BackendBalancingFactory
                .createBackendBalancing(config.getBalancingPolicy(), config.getBackends());
        this.backendManager = new BackendManager(backendBalancing);
        this.executorService = Executors.newSingleThreadScheduledExecutor();
        this.logger = LoggerFactory.getLogger(SimpleCardeaServer.class);
    }

    @Override
    public void start() {
        this.bossGroup = NettyUtils.createEventLoopGroup(1);
        this.workerGroup = new NioEventLoopGroup(4);

        Class<? extends ServerChannel> serverChannelClazz = NettyUtils.getServerChannelClass();
        ChannelHandler channelHandler = new CardeaServerChannelInitializer(this.backendManager);

        this.logger.info("Starting backend handling tasks.");

        this.executorService
                .scheduleAtFixedRate(new CheckDeadBackendsTask(this.backendManager), 10, 10,
                        TimeUnit.SECONDS);
        this.executorService
                .scheduleAtFixedRate(new BackendRecoverTask(this.backendManager), 10, 10,
                        TimeUnit.SECONDS);

        this.logger.info("Starting server and proxying all connections on *:",
                this.config.getServerPort());

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        try {
            serverBootstrap
                    .channel(serverChannelClazz)
                    .group(this.bossGroup, this.workerGroup)
                    .childHandler(channelHandler)
                    .childOption(ChannelOption.AUTO_READ, false)
                    .bind(this.config.getServerPort())
                    .sync().channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        this.logger.info("Started reverse proxy on *:", this.config.getServerPort());
    }

    @Override
    public void stop() {
        this.bossGroup.shutdownGracefully();
        this.workerGroup.shutdownGracefully();
    }
}
