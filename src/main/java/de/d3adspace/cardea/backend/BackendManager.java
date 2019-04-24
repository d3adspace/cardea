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

package de.d3adspace.cardea.backend;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class BackendManager {

    private final List<Backend> idlingBackends = new CopyOnWriteArrayList<>();
    private final BackendBalancing backendBalancing;

    public BackendManager(BackendBalancing backendBalancing) {
        this.backendBalancing = backendBalancing;
    }

    /**
     * Get the next eligible backend.
     *
     * @return The backend.
     */
    public Backend nextBackend() {

        return backendBalancing.getBackend();
    }

    /**
     * Get all backends.
     *
     * @return The backends.
     */
    public List<Backend> getBackends() {

        return backendBalancing.getBackends();
    }

    public void removeBackend(Backend backend) {
        this.backendBalancing.removeBackend(backend);
    }

    public void addIdlingBackend(Backend backend) {
        this.idlingBackends.add(backend);
    }

    public List<Backend> getIdlingBackends() {
        return idlingBackends;
    }

    public void addBackend(Backend backend) {

        backendBalancing.registerBackend(backend);
    }
}
