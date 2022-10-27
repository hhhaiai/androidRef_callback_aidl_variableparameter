/*
 * Copyright (C) 2021 LSPosed
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

package org.lsposed.hiddenapibypass;

import java.lang.invoke.MethodHandleInfo;
import java.lang.invoke.MethodType;
import java.lang.reflect.Member;

@SuppressWarnings("unused")
public class Helper {
    // libcore/ojluni/src/main/java/java/lang/invoke/MethodHandle.java
    // http://aospxref.com/android-11.0.0_r21/xref/libcore/ojluni/src/main/java/java/lang/invoke/MethodHandle.java
    // https://cs.android.com/android/platform/superproject/+/master:libcore/ojluni/src/main/java/java/lang/invoke/MethodHandle.java
    //public abstract class MethodHandle
    static public class MethodHandle {
        private final MethodType type = null;

         private MethodType nominalType;
        //The spread invoker associated with this type with zero trailing arguments.  This is used to speed up invokeWithArguments.
        //与此类型关联的扩展调用程序，带有零个尾随参数。 这用于加速 invokeWithArguments。
        private MethodHandle cachedSpreadInvoker;
        // The kind of this method handle (used by the runtime). This is one of the INVOKE_* constants or SGET/SPUT, IGET/IPUT.
        //此方法句柄的类型（由运行时使用）。 这是 INVOKE_* 常量或 SGET/SPUT、IGET/IPUT 之一。
        protected final int handleKind = 0;

        // The ArtMethod* or ArtField* associated with this method handle (used by the runtime).
        //与此方法句柄关联的 ArtMethod* 或 ArtField*（由运行时使用）。
        protected final long artFieldOrMethod = 0;
    }

    // libcore/ojluni/src/main/java/java/lang/invoke/MethodHandleImpl.java
    // http://aospxref.com/android-11.0.0_r21/xref/libcore/ojluni/src/main/java/java/lang/invoke/MethodHandleImpl.java
    // https://cs.android.com/android/platform/superproject/+/master:libcore/ojluni/src/main/java/java/lang/invoke/MethodHandleImpl.java
    // A method handle that's directly associated with an ArtField or an ArtMethod and specifies no additional transformations.
    //与 ArtField 或 ArtMethod 直接关联且不指定其他转换的方法句柄。
    //public class MethodHandleImpl extends MethodHandle implements Cloneable
    static final public class MethodHandleImpl extends MethodHandle {
        private final MethodHandleInfo info = null;
    }

    // libcore/ojluni/src/main/java/java/lang/invoke/MethodHandleImpl.java
    // http://aospxref.com/android-11.0.0_r21/xref/libcore/ojluni/src/main/java/java/lang/invoke/MethodHandleImpl.java#69
    // https://cs.android.com/android/platform/superproject/+/master:libcore/ojluni/src/main/java/java/lang/invoke/MethodHandleImpl.java
    //Implementation of {@code MethodHandleInfo} in terms of the handle being cracked and its corresponding {@code java.lang.reflect.Member}.
    //{@code MethodHandleInfo} 在被破解句柄及其对应的 {@code java.lang.reflect.Member} 方面的实现。
    //static class HandleInfo implements MethodHandleInfo
    // java/lang/invoke/MethodHandleImpl$HandleInfo
    static final public class HandleInfo {
        private final Member member = null;
        private final MethodHandle handle = null;
    }

    // libcore/ojluni/src/main/java/java/lang/Class.java
    // http://aospxref.com/android-11.0.0_r21/xref/libcore/ojluni/src/main/java/java/lang/Class.java
    // https://cs.android.com/android/platform/superproject/+/master:libcore/ojluni/src/main/java/java/lang/Class.java
    //public final class Class<T> implements java.io.Serializable, GenericDeclaration, Type, AnnotatedElement
    static final public class Class {
        /** defining class loader, or null for the "bootstrap" system loader. */
        private transient ClassLoader classLoader;
        /**
         * For array classes, the component class object for instanceof/checkcast (for String[][][],
         * this will be String[][]). null for non-array classes.
         */
        private transient java.lang.Class<?> componentType;
        /**
         * DexCache of resolved constant pool entries. Will be null for certain runtime-generated classes
         * e.g. arrays and primitive classes.
         */
        private transient Object dexCache;
        /**
         * Extra data that only some classes possess. This is allocated lazily as needed.
         */
        private transient Object extData;
        /**
         * The interface table (iftable_) contains pairs of a interface class and an array of the
         * interface methods. There is one pair per interface supported by this class.  That
         * means one pair for each interface we support directly, indirectly via superclass, or
         * indirectly via a superinterface.  This will be null if neither we nor our superclass
         * implement any interfaces.
         *
         * Why we need this: given "class Foo implements Face", declare "Face faceObj = new Foo()".
         * Invoke faceObj.blah(), where "blah" is part of the Face interface.  We can't easily use a
         * single vtable.
         *
         * For every interface a concrete class implements, we create an array of the concrete vtable_
         * methods for the methods in the interface.
         */
        private transient Object[] ifTable;
        /** Lazily computed name of this class; always prefer calling getName(). */
        private transient String name;
        /** The superclass, or null if this is java.lang.Object, an interface or primitive type. */
        private transient java.lang.Class<?> superClass;
        /**
         * Virtual method table (vtable), for use by "invoke-virtual". The vtable from the superclass
         * is copied in, and virtual methods from our class either replace those from the super or are
         * appended. For abstract classes, methods may be created in the vtable that aren't in
         * virtual_ methods_ for miranda methods.
         */
        private transient Object vtable;
        /**
         * Instance fields. These describe the layout of the contents of an Object. Note that only the
         * fields directly declared by this class are listed in iFields; fields declared by a
         * superclass are listed in the superclass's Class.iFields.
         *
         * All instance fields that refer to objects are guaranteed to be at the beginning of the field
         * list.  {@link Class#numReferenceInstanceFields} specifies the number of reference fields.
         */
        private transient long iFields;
        /** All methods with this class as the base for virtual dispatch. */
        private transient long methods;
        /** Static fields */
        private transient long sFields;
        /** access flags; low 16 bits are defined by VM spec */
        private transient int accessFlags;
        /** Class flags to help the GC with object scanning. */
        private transient int classFlags;
        /**
         * Total size of the Class instance; used when allocating storage on GC heap.
         * See also {@link Class#objectSize}.
         */
        private transient int classSize;
        /**
         * tid used to check for recursive static initializer invocation.
         */
        private transient int clinitThreadId;
        /**
         * Class def index from dex file. An index of 65535 indicates that there is no class definition,
         * for example for an array type.
         * TODO: really 16bits as type indices are 16bit.
         */
        private transient int dexClassDefIndex;
        /**
         * Class type index from dex file, lazily computed. An index of 65535 indicates that the type
         * index isn't known. Volatile to avoid double-checked locking bugs.
         * TODO: really 16bits as type indices are 16bit.
         */
        private transient volatile int dexTypeIndex;
        /** Number of instance fields that are object references. */
        private transient int numReferenceInstanceFields;
        /** Number of static fields that are object references. */
        private transient int numReferenceStaticFields;
        /**
         * Total object size; used when allocating storage on GC heap. For interfaces and abstract
         * classes this will be zero. See also {@link Class#classSize}.
         */
        private transient int objectSize;
        /**
         * Aligned object size for allocation fast path. The value is max int if the object is
         * uninitialized or finalizable, otherwise the aligned object size.
         */
        private transient int objectSizeAllocFastPath;
        /**
         * The lower 16 bits is the primitive type value, or 0 if not a primitive type; set for
         * generated primitive classes.
         */
        private transient int primitiveType;
        /** Bitmap of offsets of iFields. */
        private transient int referenceInstanceOffsets;
        /** State of class initialization */
        private transient int status;
        /** Offset of the first virtual method copied from an interface in the methods array. */
        private transient short copiedMethodsOffset;
        /** Offset of the first virtual method defined in this class in the methods array. */
        private transient short virtualMethodsOffset;

        //Class.forName()
        // 等价于
        // Class<?> caller = Reflection.getCallerClass();
        //return forName(className, true, ClassLoader.getClassLoader(caller));
        // 也可以考虑下面方案，直接用了这个加载器
        //classForName(name, trye, BootClassLoader.getInstance());
    }

    // libcore/ojluni/src/main/java/java/lang/reflect/AccessibleObject.java
    // http://aospxref.com/android-11.0.0_r21/xref/libcore/ojluni/annotations/hiddenapi/java/lang/reflect/AccessibleObject.java
    // https://cs.android.com/android/platform/superproject/+/master:libcore/ojluni/src/main/java/java/lang/reflect/AccessibleObject.java
    // public class AccessibleObject implements AnnotatedElement
    static public class AccessibleObject {

        // Indicates whether language-level access checks are overridden
        // by this object. Initializes to "false". This field is used by
        // Field, Method, and Constructor.
        //
        // NOTE: for security purposes, this field must not be visible
        // outside this package.
        boolean override;
//        private boolean override;
    }

    ////http://androidxref.com/7.0.0_r1/xref/libcore/libart/src/main/java/java/lang/reflect/AbstractMethod.java
    //http://aospxref.com/android-11.0.0_r21/xref/libcore/ojluni/src/main/java/java/lang/reflect/Parameter.java
    //
    // 获取方式： Method.class.getSuperclass()
    // Before Oreo, it is: java.lang.reflect.AbstractMethod
    // After Oreo, it is: java.lang.reflect.Executable
    // https://cs.android.com/android/platform/superproject/+/master:libcore/ojluni/src/main/java/java/lang/reflect/Executable.java
    // public abstract class Executable extends AccessibleObject implements Member, GenericDeclaration
    static final public class Executable extends AccessibleObject {

        /** Executable's declaring class */
        private Class declaringClass;

        /** The method index of this method within its defining dex file */
        private int dexMethodIndex;
        /**
         * Overriden method's declaring class (same as declaringClass unless declaringClass is a proxy  class).
         *  private Class<?> declaringClassOfOverriddenMethod;
         */
        private Class declaringClassOfOverriddenMethod;
        // android 7 has not
        // android O以上才有 Executable有
        // AbstractMethod木有
        // 应该是这个 private transient volatile Parameter[] parameters;
        private Object[] parameters;
        private transient volatile boolean hasRealParameterData;



        // http://androidxref.com/7.0.0_r1/xref/libcore/libart/src/main/java/java/lang/reflect/AbstractMethod.java
        // http://androidxref.com/7.1.1_r6/xref/libcore/libart/src/main/java/java/lang/reflect/AbstractMethod.java
        // http://androidxref.com/7.1.1_r6/xref/libcore/libart/src/main/java/java/lang/reflect/AbstractMethod.java
        // http://androidxref.com/7.1.2_r36/xref/libcore/libart/src/main/java/java/lang/reflect/AbstractMethod.java
        // java.lang.reflect.AbstractMethod  artMethod
        // http://androidxref.com/8.0.0_r4/xref/libcore/ojluni/src/main/java/java/lang/reflect/Executable.java
        // http://androidxref.com/8.1.0_r33/xref/libcore/ojluni/src/main/java/java/lang/reflect/Executable.java
        // http://androidxref.com/9.0.0_r3/xref/libcore/ojluni/src/main/java/java/lang/reflect/Executable.java
        // java.lang.reflect.Executable artMethod
        /**
         * The ArtMethod associated with this Executable, required for dispatching due to entrypoints
         * Classloader is held live by the declaring class.
         */
        private long artMethod;

        /**
         * BEGIN Android-added: Additional ART-related fields and logic.
         * This code is shared for Method and Constructor.
         * Bits encoding access (e.g. public, private) as well as other runtime specific flags
         */
        private int accessFlags;
    }

//    protected long artMethod;
    //  protected int accessFlags;




    @SuppressWarnings("EmptyMethod")
    public static class NeverCall {
        private static void a() {
        }

        private static void b() {
        }

        private static int s;
        private static int t;
        private int i;
        private int j;
    }

    public static class InvokeStub {
        private static Object invoke(Object... args) {
            throw new IllegalStateException("Failed to invoke the method");
        }

        private InvokeStub(Object... args) {
            throw new IllegalStateException("Failed to new a instance");
        }
    }
}
