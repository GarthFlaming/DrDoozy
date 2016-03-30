package engineTester;
 
import models.RawModel;
import models.TexturedModel;
import objConverter.ModelData;
import objConverter.OBJFileLoader;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;
 
import renderEngine.DisplayManager;
import renderEngine.Loader;
import renderEngine.MasterRenderer;
import renderEngine.OBJLoader;
import terrains.Terrain;
import textures.ModelTexture;
import textures.TerrainTexture;
import textures.TerrainTexturePack;
import toolbox.MousePicker;
import entities.Camera;
import entities.Entity;
import entities.Light;
import entities.Player;
import guis.GuiRenderer;
import guis.GuiTexture;
 
public class MainGameLoop {
 
    public static void main(String[] args) {
 
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        
        //********* Terrain Texture stuff
        
        TerrainTexture backgroundTexture = new TerrainTexture(loader.loadTexture("grassy"));
        TerrainTexture rTexture = new TerrainTexture(loader.loadTexture("dirt"));
        TerrainTexture gTexture = new TerrainTexture(loader.loadTexture("pinkFlowers"));
        TerrainTexture bTexture = new TerrainTexture(loader.loadTexture("path"));
        
        TerrainTexturePack texturePack = new TerrainTexturePack(backgroundTexture, rTexture, gTexture,bTexture);
        TerrainTexture blendMap = new TerrainTexture(loader.loadTexture("blendMap"));
        
        //********
         
       RawModel model = OBJLoader.loadObjModel("pine",loader);
         
        TexturedModel staticModel = new TexturedModel(model, new ModelTexture(loader.loadTexture("pine")));
        
        TexturedModel grass = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
        		new ModelTexture(loader.loadTexture("grassTexture")));
        
        TexturedModel flower = new TexturedModel(OBJLoader.loadObjModel("grassModel", loader),
        		new ModelTexture(loader.loadTexture("flower")));
        
        TexturedModel lamp = new TexturedModel(OBJLoader.loadObjModel("lamp", loader),
        		new ModelTexture(loader.loadTexture("lamp")));
        
       
        ModelTexture fernTextureAtlas = new ModelTexture(loader.loadTexture("fern"));
        fernTextureAtlas.setNumberOfRows(2);
        
        TexturedModel fern = new TexturedModel(OBJLoader.loadObjModel("fern", loader), fernTextureAtlas);
        
        
        TexturedModel bobble = new TexturedModel(OBJLoader.loadObjModel("lowPolyTree", loader),
        		new ModelTexture(loader.loadTexture("lowPolyTree")));
        
        grass.getTexture().setHasTransparency(true);
        grass.getTexture().setUseFakeLighting(true);
        flower.getTexture().setHasTransparency(true);
        flower.getTexture().setUseFakeLighting(true);
        fern.getTexture().setHasTransparency(true);
        
        Terrain terrain = new Terrain(0, -1, loader, texturePack, blendMap, "heightmap");
        
        List<Entity> entities = new ArrayList<Entity>();
        Random random = new Random(676452);
        for(int i = 0;i < 400; i++){
        	
        	if(i%2 == 0){
        		
        		float x = random.nextFloat() * 800 -400;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
       
        		entities.add(new Entity(fern, random.nextInt(4), new Vector3f(x, y, z), 0, random.nextFloat() * 360,
        				0, 0.9f));
        		
        	}
        	
        	
        	if(i % 7 == 0){
        		float x = random.nextFloat() * 800 -400;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(grass, new Vector3f(x, y, z), 0, 0, 0, 2.3f));
        	}
        	if(i % 3 == 0){
        		float x = random.nextFloat() * 800 -400;
        		float z = random.nextFloat() * -600;
        		float y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(fern, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, 0.9f));
        		x = random.nextFloat() * 800 -400;
        		z = random.nextFloat() * -600;
        		y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(bobble, new Vector3f(x, y, z), 0, random.nextFloat() * 360, 0, random.nextFloat() * 0.1f + 0.6f));
        		x = random.nextFloat() * 800 -400;
        		z = random.nextFloat() * -600;
        		y = terrain.getHeightOfTerrain(x, z);
        		entities.add(new Entity(staticModel, new Vector3f(x, y, z), 0, 0, 0,random.nextFloat() * 1 + 4));
        	}
        }
        
        List<Light> lights = new ArrayList<Light>();
        lights.add(new Light(new Vector3f(0, 1000, -7000), new Vector3f(0.5f, 0.5f, 0.5f))); //the sun
        lights.add(new Light(new Vector3f(185, 10, -293), new Vector3f(2, 0, 0), new Vector3f(1,0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(370, 17, -300), new Vector3f(0, 2, 2), new Vector3f(1,0.01f, 0.002f)));
        lights.add(new Light(new Vector3f(293, 7, -305), new Vector3f(2, 2, 0), new Vector3f(1,0.01f, 0.002f)));
        
        entities.add(new Entity(lamp, new Vector3f(185, -4.7f, -293), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(370, 4.2f, -300), 0, 0, 0, 1));
        entities.add(new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0 , 0, 1));
        
        MasterRenderer renderer = new MasterRenderer(loader);
        
        RawModel personModel = OBJLoader.loadObjModel("person", loader);
        TexturedModel person = new TexturedModel(personModel, new ModelTexture(loader.loadTexture("playerTexture")));
        
        Player player = new Player(person, new Vector3f(100,0,-50),0,-200,0,1);
        Camera camera = new Camera(player);
        
        List<GuiTexture> guis = new ArrayList<GuiTexture>();
        GuiTexture gui = new GuiTexture(loader.loadTexture("health"), new Vector2f(-0.75f, 0.9f), new Vector2f(0.25f, 0.25f));
        guis.add(gui);
        
        GuiRenderer guiRenderer = new GuiRenderer(loader);
        
        MousePicker picker = new MousePicker(camera, renderer.getProjectionMatrix(), terrain);
        
        Entity lampEntity = (new Entity(lamp, new Vector3f(293, -6.8f, -305), 0, 0, 0, 1)); 
        entities.add(lampEntity);
        
        Light light = (new Light(new Vector3f(290, 7, -305), new Vector3f(0, 2, 2), new Vector3f(1, 0.01f, 0.002f)));
        lights.add(light); 
        
        while(!Display.isCloseRequested()){
            camera.move();
            player.move(terrain);
            
            picker.update(); 
            //System.out.println(picker.getCurrentRay());
            Vector3f terrainPoint = picker.getCurrentTerrainPoint(); 
            if(terrainPoint!=null){
            	lampEntity.setPosition(terrainPoint); 
            	light.setPosition(new Vector3f(terrainPoint.x, terrainPoint.y+15, terrainPoint.z));
            }
            renderer.processEntity(player);
            renderer.processTerrain(terrain);
            for(Entity entity : entities){
            	renderer.processEntity(entity);
            }
            
            renderer.render(lights, camera);
            guiRenderer.render(guis);
            DisplayManager.updateDisplay();
        }
        
        guiRenderer.cleanUp();
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
 
    }
 
}