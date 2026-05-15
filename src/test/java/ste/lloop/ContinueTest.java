/*
 * Copyright 2025 the original author or authors from the λLoop project (https://lambda-loop.github.io/)..
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ste.lloop;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.BDDAssertions.then;
import static ste.lloop.Loop._continue_;
import static ste.lloop.Loop.on;

public class ContinueTest {

    @Test
    public void Continue_is_a_NoStackError() {
        then(new Continue()).isInstanceOf(NoStackError.class);
    }

    @Test
    public void continue_with_catch() {
        final StringBuffer sb = new StringBuffer();
        on("one", "two", "three").loop((i, s) -> {
            try {
                if (i%2  != 0) {
                    _continue_();
                }
            } catch (Exception x) {}
            sb.append(s);
        });

        then(sb).asString().isEqualTo("onethree");
    }
}
