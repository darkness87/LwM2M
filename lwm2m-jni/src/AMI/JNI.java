package AMI;

import java.io.File;
//import AMI.JniConfig;

public class JNI 
{
			
	static {
		//String path = new JniConfig().getJniPath(); // 프로퍼티 Path 사용시
		
		String path = System.getProperty("user.dir")+"/src/main/resources/";
		
        File lib = new File(path + System.mapLibraryName("JNI"));
        System.load(lib.getAbsolutePath());
    }
    
    private native String get_maker();
    private native String get_model();
    private native String get_serial_num();
    private native void exe_reboot();
    private native void exe_factory_reboot();
    private native int available_pwr_source();
    private native String get_voltage();
    private native String get_current();
    private native int get_free_memory();
    private native String get_cur_time();
    private native String set_cur_time();
    private native String get_time_zone();
    private native String set_time_zone();
    private native int get_dev_type();
    private native String get_hw_ver();
    private native int get_total_memory();
    
    private native int get_rsrp();
    private native int get_rsrq();
    private native String get_ip_address();
    private native int get_cell_id();
    private native int get_mnc();
    private native int get_mcc();
    private native int get_snr();
    
    private native String get_fw_ver();
    
    private native int get_tx_data_count();
    private native int get_rx_data_count();
    private native int set_trx_data_clear();
    private native int exe_data_collect_start();
    private native int exe_data_collect_stop();
    private native int get_data_collect_period();
    private native int set_data_collect_period();
    
    private native String get_ami_name(); 
    private native int get_ami_type();
    private native String get_ami_date();
    private native String get_cpu_maker();
    private native String get_cpu_model();
    private native String get_cpu_rate(); 
    private native String get_ram_rate();
    private native String get_mear_pwr_vlt();
    private native String get_mear_cons_cur();
    private native void exe_factory_reboot_amicc();
    private native void exe_last_reboot(); 
    private native String get_pwr_off_time();
    private native String get_pwr_on_time();
    private native int exe_wdt_test();
    private native int get_self_reset_period();
    private native int set_self_reset_period(); 
    private native int get_self_reset_time();
    private native int set_self_reset_time();
    private native int get_rs485dlb_period();
    private native int set_rs485dlb_period();
    private native void exe_rs485dlb_start(); 
    private native void exe_rs485dlb_stop();
    private native String set_rs485dlb_test_input_data();
    private native String get_rs485dlb_test_input_data();
    private native String get_rs485dlb_test_output_data();
    private native void exe_led_test();
    
    private native int get_wan_code();
    private native int get_communication_code();
    private native int get_telecom_company();
    private native String get_phone_number();
    private native int get_tx_power();
    
    private native String get_os_name();
    private native String get_os_version();
    
    private native String get_cypher_module_version();
    private native String get_cypher_sw_version(); 
    private native void exe_cypher_test_start();
    private native void exe_cypher_test_stop();
    private native void exe_integrity_test_start();
    private native void exe_integrity_test_stop();
    
    private native void exe_module_open(); 
    private native String get_date_time();
    
    public static void main(String[] args){
        JNI jni = new JNI();
        
        System.out.println(jni.get_maker());
        System.out.println(jni.get_model());
        System.out.println(jni.get_serial_num());
        jni.exe_reboot();
        jni.exe_factory_reboot();
        System.out.println(jni.available_pwr_source());
        System.out.println(jni.get_voltage());
    }
    
    public String getMaker() {
    	JNI jni = new JNI();
    	String data = jni.get_maker();
    	return data;
    }
    public String getModel() {
    	JNI jni = new JNI();
    	String data = jni.get_model();
    	return data;
    }
    public String getSerialNum() {
    	JNI jni = new JNI();
    	String data = jni.get_serial_num();
    	return data;
    }
    public void exeReboot() {
    	JNI jni = new JNI();
    	jni.exe_reboot();
    }
    public void exeFactoryReboot() {
    	JNI jni = new JNI();
    	jni.exe_factory_reboot();
    }
    public int availablePwrSource() {
    	JNI jni = new JNI();
    	int data = jni.available_pwr_source();
    	return data;
    }
    public String getVoltage() {
    	JNI jni = new JNI();
    	String data = jni.get_voltage();
    	return data;
    }
    
}
