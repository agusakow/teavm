/*
 *  Copyright 2016 Alexey Andreev.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.teavm.platform.plugin;

import java.io.IOException;
import org.teavm.codegen.SourceWriter;
import org.teavm.javascript.spi.Generator;
import org.teavm.javascript.spi.GeneratorContext;
import org.teavm.model.MethodReference;
import org.teavm.model.ValueType;

public class PlatformClassGenerator implements Generator {
    @Override
    public void generate(GeneratorContext context, SourceWriter writer, MethodReference methodRef) throws IOException {
        switch (methodRef.getName()) {
            case "asJavaClass":
                writer.append("function $rt_cls(cls)").ws().append("{").softNewLine().indent();
                writer.append("return ").appendMethodBody("java.lang.Class", "getClass",
                        ValueType.object("org.teavm.platform.PlatformClass"),
                        ValueType.object("java.lang.Class")).append("(cls);")
                        .softNewLine();
                writer.outdent().append("}").newLine();
        }
    }
}
