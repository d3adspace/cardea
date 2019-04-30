/*
 * Copyright (c) 2017 - 2019 D3adspace
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

package de.d3adspace.cardea.server.task;

import de.d3adspace.cardea.server.backend.BackendManager;
import de.d3adspace.cardea.server.utils.SocketUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class CheckDeadBackendsTask implements Runnable {

    private final Logger logger;
    private final BackendManager backendManager;

    public CheckDeadBackendsTask(BackendManager backendManager) {
        this.logger = LoggerFactory.getLogger(CheckDeadBackendsTask.class);
        this.backendManager = backendManager;
    }

    @Override
    public void run() {
        new HashSet<>(backendManager.getBackends())
                .stream()
                .filter(backend -> !SocketUtils.isReachable(backend.getHost(), backend.getPort()))
                .forEach(backend -> {
                    backendManager.removeBackend(backend);
                    logger.info("Lost Backend: {} [{}:{}]", backend.getName(), backend.getHost(),
                            backend.getPort());
                    backendManager.addIdlingBackend(backend);
                });
    }
}
