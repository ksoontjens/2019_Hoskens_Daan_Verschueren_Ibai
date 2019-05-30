package hellotvxlet;                         // Foto's moeten in deze map --> c:/Program Files\TechnoTrend\TT-MHP-Browser\filio\DSMCC\0.0.3

import javax.tv.xlet.Xlet;
import javax.tv.xlet.XletContext;
import javax.tv.xlet.XletStateChangeException;
import org.bluray.ui.event.HRcEvent;
import org.davic.resources.ResourceClient;
import org.davic.resources.ResourceProxy;
import org.dvb.event.EventManager;
import org.dvb.event.UserEvent;
import org.dvb.event.UserEventListener;
import org.dvb.event.UserEventRepository;
import org.havi.ui.HBackgroundConfigTemplate;
import org.havi.ui.HBackgroundDevice;
import org.havi.ui.HBackgroundImage;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HScreen;
import org.havi.ui.HStillImageBackgroundConfiguration;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;


public class HelloTVXlet implements Xlet,ResourceClient,HBackgroundImageListener,UserEventListener {
   HScreen screenCover;
   HBackgroundDevice hbgDevice;
   HBackgroundConfigTemplate hbgTemplate;
   HStillImageBackgroundConfiguration hbgConfiguration;
   
   private boolean activeGamingConsole = true;
   private int imageLoaded = 0;
   private int imageCurrent = 0;
   HBackgroundImage imageXBOX[] = new HBackgroundImage[10];
   HBackgroundImage imagePS[] = new HBackgroundImage[10];
   private HScene scene;
   
    public void initXlet(XletContext ctx) throws XletStateChangeException {
        screenCover = HScreen.getDefaultHScreen();
        hbgDevice = screenCover.getDefaultHBackgroundDevice();
        
        if(hbgDevice.reserveDevice(this)){
            System.out.println("Achtergrond succesvol gereserveerd");
        }
        else {
            System.out.println("Achtergrond  onsuccesvol gereserveerd");
        }
        
        hbgTemplate = new HBackgroundConfigTemplate();
        hbgTemplate.setPreference(HBackgroundConfigTemplate.STILL_IMAGE,HBackgroundConfigTemplate.REQUIRED);
        
        hbgConfiguration = (HStillImageBackgroundConfiguration) hbgDevice.getBestConfiguration(hbgTemplate);
        
        try {
            hbgDevice.setBackgroundConfiguration(hbgConfiguration);
        } 
        catch (Exception ex){
            ex.printStackTrace();
        }
                
        imageXBOX[0] = new HBackgroundImage("AssassinsCreedXBOX.jpg");	           
        imageXBOX[1] = new HBackgroundImage("BattlefieldXBOX.jpg");	   
        imageXBOX[2] = new HBackgroundImage("BlackOps4XBOX.jpg");	   
        imageXBOX[3] = new HBackgroundImage("F1XBOX.jpg");	   
        imageXBOX[4] = new HBackgroundImage("Fifa19XBOX.jpg");	   
        imageXBOX[5] = new HBackgroundImage("AnthemXBOX.jpg");	   
        imageXBOX[6] = new HBackgroundImage("MortalKombatXBOX.jpg");	   
        imageXBOX[7] = new HBackgroundImage("RedDeadRedemptionXBOX.jpg");	   
        imageXBOX[8] = new HBackgroundImage("SekiroXBOX.jpg");	
        imageXBOX[9] = new HBackgroundImage("GhostReconXBOX.jpg");  
        
        for(int i = 0; i<10; i++){
            imageXBOX[i].load(this);
        }
        
        imagePS[0] = new HBackgroundImage("AssassinsCreedPS.jpg");	           
        imagePS[1] = new HBackgroundImage("BattlefieldPS.jpg");	   
        imagePS[2] = new HBackgroundImage("BlackOps4PS.jpg");	   
        imagePS[3] = new HBackgroundImage("F1PS.jpg");	   
        imagePS[4] = new HBackgroundImage("Fifa19PS.jpg");	   
        imagePS[5] = new HBackgroundImage("AnthemPS.jpg");	   
        imagePS[6] = new HBackgroundImage("MortalKombatPS.jpg");	   
        imagePS[7] = new HBackgroundImage("RedDeadRedemptionPS.jpg");	   
        imagePS[8] = new HBackgroundImage("SekiroPS.jpg");	
        imagePS[9] = new HBackgroundImage("GhostReconPS.jpg");      
        
        for(int i = 0; i<10; i++){
            imagePS[i].load(this);
        }
                
        UserEventRepository eventRepo = new UserEventRepository("name");
        eventRepo.addAllArrowKeys();
        eventRepo.addKey(HRcEvent.VK_ENTER);
        EventManager.getInstance().addUserEventListener(this,eventRepo);

        scene = HSceneFactory.getInstance().getDefaultHScene();  
        
        scene.validate();        
        scene.setVisible(true);         
    }
    
    public void userEventReceived(UserEvent e) {
        
        if(e.getType() == HRcEvent.KEY_PRESSED){
            if(e.getCode()== HRcEvent.VK_ENTER){
             
            }
            if (e.getCode() == HRcEvent.VK_UP) {	   
                activeGamingConsole ^= true;
            }           	   
	    if (e.getCode() == HRcEvent.VK_RIGHT || e.getCode() == HRcEvent.VK_LEFT) {	   
                if(e.getCode() == HRcEvent.VK_RIGHT) {
                    if (imageCurrent < 9) {
                        imageCurrent++;	   
                    }
                }
                if(e.getCode() == HRcEvent.VK_LEFT) {
                    if (imageCurrent > 0) {
                        imageCurrent--;	   

                    }
                }
            }	   
            if ( e.getCode() == HRcEvent.VK_DOWN) {	   	   	   
                imageCurrent = 0;
                activeGamingConsole = true;
	    }
        }
	   
        try {
            if(activeGamingConsole == true) {
                hbgConfiguration.displayImage(imageXBOX[imageCurrent]);
            }
            if(activeGamingConsole == false) {
                hbgConfiguration.displayImage(imagePS[imageCurrent]);
            }
        } catch ( Exception ex ) {	 
            ex.printStackTrace();	  
        }
    }	
    
    public void imageLoaded(HBackgroundImageEvent e){
        System.out.println("Image succesvol geladen");
        imageLoaded++;
        
        if(imageLoaded == 9){
            try{
                hbgConfiguration.displayImage(imageXBOX[0]);
            }
            catch(Exception ex){
                ex.printStackTrace();
            }
        }
    }
    
    public void imageLoadFailed(HBackgroundImageEvent e) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void startXlet(){
        
    }
    public void pauseXlet() {
        
    } 
    public boolean requestRelease(ResourceProxy proxy, Object requestData) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void release(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void notifyRelease(ResourceProxy proxy) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
    public void destroyXlet(boolean unconditional) throws XletStateChangeException {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
