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

import de.d3adspace.cardea.CardeaServer;
import de.d3adspace.cardea.CardeaServerFactory;
import de.d3adspace.cardea.backend.Backend;
import de.d3adspace.cardea.backend.BackendBalancingType;
import de.d3adspace.cardea.config.CardeaConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Felix 'SasukeKawaii' Klauke
 */
public class CardeaServerTest {

    public static void main(String[] args) {
        List<Backend> backends = new ArrayList<>();
        backends.add(new Backend("Backend #1", "{host}", 1234));
        backends.add(new Backend("Backend #2", "{host}", 1235));
        backends.add(new Backend("Backend #3", "{host}", 1236));

        CardeaConfig cardeaConfig = new CardeaConfig(8081, backends,
                BackendBalancingType.ROUND_ROBIN);

        CardeaServer cardeaServer = CardeaServerFactory.createCardeaServer(cardeaConfig);
        cardeaServer.start();
    }
}
