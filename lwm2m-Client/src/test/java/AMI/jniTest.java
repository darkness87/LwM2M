package AMI;

public class jniTest 
{
    
    public static void main(String[] args){
    	JNI jni = new JNI();
        
        // 표준객체 : Device , Object : 3
        System.out.println(jni.getMaker());
        System.out.println(jni.getModel());
        System.out.println(jni.getSerialNum());
        jni.exeReboot();
        jni.exeFactoryReboot();
        System.out.println(jni.availablePwrSource());
        System.out.println(jni.getVoltage());
        
    }
    
}
