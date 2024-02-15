// TSCModule.java

package com.sample;

import android.widget.Toast;

import com.example.tscdll.TscWifiActivity;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.ReactMethod;

import java.util.Map;
import java.util.HashMap;

import com.example.tscdll.TSCActivity;


public class TSCModule extends ReactContextBaseJavaModule {
  private static ReactApplicationContext reactContext;
  private static final String DURATION_SHORT_KEY = "SHORT";
  private static final String DURATION_LONG_KEY = "LONG";
  private TSCActivity bt_api;
  private TscWifiActivity wifi_api;

  TSCModule(ReactApplicationContext context) {
    super(context);
    reactContext = context;
  }


  @Override
  public void initialize() {
    super.initialize();
    bt_api = new TSCActivity();
    wifi_api = new TscWifiActivity();
  }



  @Override
  public String getName() {
    return "TSCExample";
  }


  @Override
  public Map<String, Object> getConstants() {
    final Map<String, Object> constants = new HashMap<>();
    constants.put(DURATION_SHORT_KEY, Toast.LENGTH_SHORT);
    constants.put(DURATION_LONG_KEY, Toast.LENGTH_LONG);
    return constants;
  }

  @ReactMethod
  public void TSCPrintLabel() {

	  wifi_api.openport("192.168.1.104", 9100);
	  String printer_status = wifi_api.status();
	            	
	  wifi_api.sendcommand("SIZE 75 mm, 50 mm\r\n");
    //wifi_api.sendcommand("GAP 2 mm, 0 mm\r\n");//Gap media
	  //wifi_api.sendcommand("BLINE 2 mm, 0 mm\r\n");//blackmark media
	  wifi_api.clearbuffer();
	  wifi_api.sendcommand("SPEED 4\r\n");
	  wifi_api.sendcommand("DENSITY 12\r\n");
	  wifi_api.sendcommand("CODEPAGE UTF-8\r\n");
	  wifi_api.sendcommand("SET TEAR ON\r\n");
	  wifi_api.sendcommand("SET COUNTER @1 1\r\n");
	  wifi_api.sendcommand("@1 = \"0001\"\r\n");
	  wifi_api.sendcommand("TEXT 100,300,\"ROMAN.TTF\",0,12,12,@1\r\n");
	  wifi_api.sendcommand("TEXT 100,400,\"ROMAN.TTF\",0,12,12,\"TEST FONT\"\r\n");
	  wifi_api.barcode(100, 100, "128", 100, 1, 0, 3, 3, "123456789");
	  wifi_api.printerfont(100, 250, "3", 0, 1, 1, "987654321");
	  wifi_api.printlabel(2, 1);
	            	
	  wifi_api.closeport(5000);

    //Toast.makeText(getReactApplicationContext(), "Test", 2000).show();
    Toast.makeText(getReactApplicationContext(), "Status: "+printer_status, 2000).show();
  }


}
