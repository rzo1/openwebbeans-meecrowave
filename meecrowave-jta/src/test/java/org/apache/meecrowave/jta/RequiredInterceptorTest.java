/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.meecrowave.jta;

import org.apache.meecrowave.Meecrowave;
import org.apache.meecrowave.junit.MeecrowaveRule;
import org.junit.Rule;
import org.junit.Test;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.SystemException;
import jakarta.transaction.TransactionManager;
import jakarta.transaction.Transactional;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

public class RequiredInterceptorTest {
    @Rule
    public final MeecrowaveRule rule = new MeecrowaveRule(
            new Meecrowave.Builder().includePackages(RequiredInterceptorTest.class.getName()), "")
            .inject(this);

    @Inject
    private Tx tx;

    @Test
    public void run() throws IOException {
        tx.asserts();
    }

    @Transactional
    @ApplicationScoped
    public static class Tx {
        @Inject
        private TransactionManager manager;

        public void asserts() {
            try {
                assertNotNull(manager.getTransaction());
            } catch (final SystemException e) {
                fail(e.getMessage());
            }
        }
    }
}
