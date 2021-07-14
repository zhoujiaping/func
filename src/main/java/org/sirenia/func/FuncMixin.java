package org.sirenia.func;

import org.sirenia.func.anno.SourceMixin;
import org.sirenia.func.core.TextFunc;
import org.sirenia.func.core.ArrayFunc;
import org.sirenia.func.core.ByteCodeFunc;
import org.sirenia.func.core.CollectionFunc;
import org.sirenia.func.core.DateFunc;
import org.sirenia.func.core.EnumFunc;
import org.sirenia.func.core.FunctionFunc;
import org.sirenia.func.core.IOFunc;
import org.sirenia.func.core.ObjectFunc;
import org.sirenia.func.core.ReflectFunc;
import org.sirenia.func.core.ThreadFunc;
import org.sirenia.func.core.ZipFunc;

@SourceMixin(name = "org.sirenia.func.Func",
        value = {ArrayFunc.class, ByteCodeFunc.class,CollectionFunc.class, DateFunc.class,
                EnumFunc.class, FunctionFunc.class, IOFunc.class,
                ObjectFunc.class, ReflectFunc.class, TextFunc.class, ThreadFunc.class,
                ZipFunc.class
        })
public abstract class FuncMixin {
}
