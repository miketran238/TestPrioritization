package edu.utdallas;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;


class MyClassFileTransform implements ClassFileTransformer {

	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {
		if(className.contains("org/joda/time") || className.contains("org/apache/commons/dbutils")
				|| className.contains("org/ansj") || className.contains("org/apache/commons/imaging")
				|| className.contains("com/threerings"))
		{
			ClassReader cr = new ClassReader(classfileBuffer);
			ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
			ClassTransformVisitor ca = new ClassTransformVisitor(cw);
			cr.accept(ca, 0);
			return cw.toByteArray();
		}
		
		return classfileBuffer;
	}
}
