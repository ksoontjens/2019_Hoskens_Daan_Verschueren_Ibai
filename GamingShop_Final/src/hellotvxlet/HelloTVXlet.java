package hellotvxlet;                         // Foto's moeten in  map --> c:/Program Files\TechnoTrend\TT-MHP-Browser\filio\DSMCC\0.0.3

import java.awt.Font;
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
import org.havi.ui.HComponent;
import org.havi.ui.HScene;
import org.havi.ui.HSceneFactory;
import org.havi.ui.HScreen;
import org.havi.ui.HStaticText;
import org.havi.ui.HStillImageBackgroundConfiguration;
import org.havi.ui.HVisible;
import org.havi.ui.event.HBackgroundImageEvent;
import org.havi.ui.event.HBackgroundImageListener;

import java.awt.Graphics;
import org.dvb.ui.DVBColor;
import org.havi.ui.HTextButton;


public class HelloTVXlet implements Xlet,ResourceClient,HBackgroundImageListener,UserEventListener {
   HScreen screenCover;
   HBackgroundDevice hbgDevice;
   HBackgroundConfigTemplate hbgTemplate;
   HStillImageBackgroundConfiguration hbgConfiguration;
   
   private boolean activeGamingConsole = true;
   private int totalShoppingCartNbr = 0;
   private int imageLoaded = 0;
   private int imageCurrent = 0;
   HBackgroundImage imageXBOX[] = new HBackgroundImage[10];
   HBackgroundImage imagePS[] = new HBackgroundImage[10];
   String price[] = {"€44,99", "€55,00", "€59,99", "€19,99", "€49,99", "€34,99", "€49,99", "€44,99", "€34,99", "€59,99"};
   
   private HScene scene;
   private HStaticText priceOfGame;
   private HStaticText totalShoppingCartText;
   private HStaticText headerShoppingCartText;
   
   private HTextButton button1;
   private HTextButton button2;

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

        totalShoppingCartText = new HStaticText("\n",410,85,280,400);
        priceOfGame = new HStaticText("\n",-50,320,320,375);
        
        headerShoppingCartText = new HStaticText("Winkelwagen:",420,-100,280,400);
        headerShoppingCartText.setFont(new Font("Times New Roman", Font.BOLD, 32));
        priceOfGame.setFont(new Font("Times New Roman", Font.BOLD, 32));
        
        MyComponent shoppingCartGradient = new MyComponent (420,65,280,400);
        MyComponent priceGradient = new MyComponent (25,480,175,50);
        
        button1 = new HTextButton ( "XBOX" );
        button1. setLocation(435,500);
        button1. setSize (100 ,50);
        button1. setBackground (new DVBColor(0 ,0 ,0 ,255) );
        button1. setBackgroundMode ( HVisible .BACKGROUND_FILL);
        
        button2 = new HTextButton ( "PS" );
        button2. setLocation(575,500);
        button2. setSize (100 ,50);
        button2. setBackground (new DVBColor(0 ,0 ,0 ,255) );
        button2. setBackgroundMode ( HVisible .BACKGROUND_FILL);
 
        totalShoppingCartText.setHorizontalAlignment(HStaticText.HALIGN_RIGHT); 
        totalShoppingCartText.setVerticalAlignment(HStaticText.VALIGN_TOP);
        
        scene.add(totalShoppingCartText);
        scene.add(priceOfGame);
        priceOfGame.setTextContent("Prijs: " + price[0], HVisible.NORMAL_STATE);	   
	priceOfGame.repaint();
        
        scene.add(headerShoppingCartText);        
        scene.add(shoppingCartGradient) ; 
        scene.add(priceGradient);        
        scene.add(button1);        
        scene.add(button2);
        button1.requestFocus();
        button1.setFocusTraversal(button2, null, null, null);
        
        scene.validate();        
        scene.setVisible(true);         
    }
    
    public void userEventReceived(UserEvent e) {
        String priceGame = priceOfGame.getTextContent(HVisible.NORMAL_STATE);
        String totalShoppingCart = totalShoppingCartText.getTextContent(HVisible.NORMAL_STATE);
        
        if(e.getType() == HRcEvent.KEY_PRESSED){
            if(e.getCode()== HRcEvent.VK_ENTER){
                if(totalShoppingCartNbr < 10) {
                    switch(imageCurrent) {
                        case 0: 
                            totalShoppingCart = totalShoppingCart + "Assassin's Creed Odyssey \n";
                            break;
                        case 1: 
                            totalShoppingCart = totalShoppingCart + "Battlefield V \n";
                            break;
                        case 2: 
                            totalShoppingCart = totalShoppingCart + "Call of Duty: Black Ops 4 \n ";
                            break;
                        case 3: 
                            totalShoppingCart = totalShoppingCart + "F1 \n"; 
                            break;
                        case 4: 
                            totalShoppingCart = totalShoppingCart + "FIFA 19 \n";
                            break;
                        case 5: 
                            totalShoppingCart = totalShoppingCart + "Anthem \n "; 
                            break;
                        case 6: 
                            totalShoppingCart = totalShoppingCart + "Mortal Kombat 11 \n "; 
                            break;
                        case 7: 
                            totalShoppingCart = totalShoppingCart + "Red Dead Redemption 2 \n"; 
                            break;
                        case 8: 
                            totalShoppingCart = totalShoppingCart + "Sekiro: Shadows Die Twice \n"; 
                            break;
                        case 9: 
                            totalShoppingCart = totalShoppingCart + "Ghost Recon: Breakpoint \n "; 	
                            break;
                    }
                    totalShoppingCartNbr++;
                }	   
	        totalShoppingCartText.setTextContent(totalShoppingCart, HVisible.NORMAL_STATE);	   
	        totalShoppingCartText.repaint();
	    }	   

            if (e.getCode() == HRcEvent.VK_UP) {	   
                button1. setFocusTraversal (button2 , null, null , null );
                button2. setFocusTraversal (button1, null , null , null );
                
                activeGamingConsole ^= true;
            }    	   
	                	   
	    if (e.getCode() == HRcEvent.VK_RIGHT || e.getCode() == HRcEvent.VK_LEFT) {	   
                if(e.getCode() == HRcEvent.VK_RIGHT) {
                    if (imageCurrent < 9) {
                        imageCurrent++;	   
                        priceGame = "";	    
                    }
                }
                if(e.getCode() == HRcEvent.VK_LEFT) {
                    if (imageCurrent > 0) {
                        imageCurrent--;	   
                        priceGame = "";	   
                    }
                }
                
                priceGame = price[imageCurrent];  
                priceOfGame.setTextContent("Prijs: " + priceGame, HVisible.NORMAL_STATE);	   
	        priceOfGame.repaint();
            }	   
            
            if ( e.getCode() == HRcEvent.VK_DOWN) {	   	   	   
                totalShoppingCart = " \n"; 
                totalShoppingCartNbr = 0;
                imageCurrent = 0;	   
                totalShoppingCartText.setTextContent(totalShoppingCart, HVisible.NORMAL_STATE);	   
                totalShoppingCartText.repaint();
                priceGame = "";
                priceOfGame.setTextContent("Prijs: " + price[0], HVisible.NORMAL_STATE);	   
	        priceOfGame.repaint();                
                button1.requestFocus();
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

class MyComponent extends HComponent {
    public MyComponent (int a, int b, int c, int d) {
        this . setBounds (a, b, c, d) ;
    }
    
    public void paint (Graphics g) {
        g . setColor (new DVBColor(0,0,0,200) );
        g . fillRect (0,0,500,500);
    }
}
