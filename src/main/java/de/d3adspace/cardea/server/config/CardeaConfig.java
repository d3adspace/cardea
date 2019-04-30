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

package de.d3adspace.cardea.server.config;

import de.d3adspace.cardea.server.backend.Backend;
import de.d3adspace.cardea.server.backend.BackendBalancingType;

import java.util.List;

/**
 * @author Felix Klauke <info@felix-klauke.de>
 */
public class CardeaConfig {

    private final int serverPort;
    private final List<Backend> backends;
    private final BackendBalancingType balancingPolicy;

    public CardeaConfig(int serverPort, List<Backend> backends,
                        BackendBalancingType balancingPolicy) {
        this.serverPort = serverPort;
        this.backends = backends;
        this.balancingPolicy = balancingPolicy;
    }

    public List<Backend> getBackends() {
        return backends;
    }

    public int getServerPort() {
        return serverPort;
    }

    public BackendBalancingType getBalancingPolicy() {
        return balancingPolicy;
    }
}