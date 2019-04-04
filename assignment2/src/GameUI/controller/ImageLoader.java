package GameUI.controller;

import javafx.scene.image.Image;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;

public class ImageLoader {
    private HashMap<String, Image> images;

    public ImageLoader(){
        this.images = new HashMap<>();

        try {
            File file = new File("src/resources/floor.png");
            FileInputStream input = new FileInputStream(file.getAbsolutePath());
            Image floorImage = new Image(input, 40, 40, true, true);
            images.put("Floor", floorImage);

            File file1 = new File("src/resources/hero.png");
            FileInputStream input1 = new FileInputStream(file1.getAbsolutePath());
            Image heroImage = new Image(input1, 40, 40, true,true);
            images.put("Hero", heroImage);

            File file2 = new File("src/resources/coward.png");
            FileInputStream input2 = new FileInputStream(file2.getAbsolutePath());
            Image coward = new Image(input2, 30, 30, true,true);
            images.put("Coward", coward);

            File file3 = new File("src/resources/wall.png");
            FileInputStream input3 = new FileInputStream(file3.getAbsolutePath());
            Image wall = new Image(input3, 40, 40, true,true);
            images.put("Wall", wall);

            File file4 = new File("src/resources/hunter.png");
            FileInputStream input4 = new FileInputStream(file4.getAbsolutePath());
            Image hunter = new Image(input4, 40, 40, true,true);
            images.put("Hunter", hunter);

            File file5 = new File("src/resources/arrow.png");
            FileInputStream input5 = new FileInputStream(file5.getAbsolutePath());
            Image arrow = new Image(input5, 20, 20, true,true);
            images.put("Arrow", arrow);

            File file6 = new File("src/resources/treasure.png");
            FileInputStream input6 = new FileInputStream(file6.getAbsolutePath());
            Image treasure = new Image(input6, 25, 25, true, true);
            images.put("Treasure", treasure);

            File file7 = new File("src/resources/sword.png");
            FileInputStream input7 = new FileInputStream(file7.getAbsolutePath());
            Image sword = new Image(input7, 25, 25, true, true);
            images.put("Sword", sword);

            File file8 = new File("src/resources/pit.png");
            FileInputStream input8 = new FileInputStream(file8.getAbsolutePath());
            Image pit = new Image(input8, 25, 25, true, true);
            images.put("Pit", pit);

            File file9 = new File("src/resources/open_door.png");
            FileInputStream input9 = new FileInputStream(file9.getAbsolutePath());
            Image open_door = new Image(input9, 40, 40, true, true);
            images.put("OpenDoor", open_door);

            File file10 = new File("src/resources/closed_door.png");
            FileInputStream input10 = new FileInputStream(file10.getAbsolutePath());
            Image door = new Image(input10, 40, 40, true, true);
            images.put("ClosedDoor", door);

            File file11 = new File("src/resources/hover_potion.png");
            FileInputStream input11 = new FileInputStream(file11.getAbsolutePath());
            Image hover = new Image(input11, 25, 25, true, true);
            images.put("Hover", hover);

            File file12 = new File("src/resources/invin_potion.png");
            FileInputStream input12 = new FileInputStream(file12.getAbsolutePath());
            Image invin = new Image(input12, 25, 25, true, true);
            images.put("Invincible", invin);

            File file13 = new File("src/resources/strategist.png");
            FileInputStream input13 = new FileInputStream(file13.getAbsolutePath());
            Image strategist = new Image(input13, 35, 35, true,true);
            images.put("Strategist", strategist);

            File file14 = new File("src/resources/hound.png");
            FileInputStream input14 = new FileInputStream(file14.getAbsolutePath());
            Image hound = new Image(input14, 30, 30, true,true);
            images.put("Hound", hound);

            File file15 = new File("src/resources/key.png");
            FileInputStream input15 = new FileInputStream(file15.getAbsolutePath());
            Image key = new Image(input15, 20, 20, true,true);
            images.put("Key", key);

            File file16 = new File("src/resources/boulder.png");
            FileInputStream input16 = new FileInputStream(file16.getAbsolutePath());
            Image boulder = new Image(input16, 35, 35, true, true);
            images.put("Boulder", boulder);
            
            File file17 = new File("src/resources/bomb_unlit.png");
            FileInputStream input17 = new FileInputStream(file17.getAbsolutePath());
            Image unlitBomb = new Image(input17, 25, 25, true, true);
            images.put("Bomb", unlitBomb);
            
            File file18 = new File("src/resources/bomb_lit_1.png");
            FileInputStream input18 = new FileInputStream(file18.getAbsolutePath());
            Image lit1Bomb = new Image(input18, 25, 25, true, true);
            images.put("FullLitBomb", lit1Bomb);
            
            File file19 = new File("src/resources/bomb_lit_2.png");
            FileInputStream input19 = new FileInputStream(file19.getAbsolutePath());
            Image lit2Bomb = new Image(input19, 25, 25, true, true);
            images.put("SemiLitBomb", lit2Bomb);
            
            File file20 = new File("src/resources/bomb_lit_3.png");
            FileInputStream input20 = new FileInputStream(file20.getAbsolutePath());
            Image lit3Bomb = new Image(input20, 25, 25, true, true);
            images.put("AlmostLitBomb", lit3Bomb);
            
            File file21 = new File("src/resources/bomb_lit_4.png");
            FileInputStream input21 = new FileInputStream(file21.getAbsolutePath());
            Image lit4Bomb = new Image(input21, 25, 25, true, true);
            images.put("ExplodeBomb", lit4Bomb);
            
            File file22 = new File("src/resources/exit.png");
            FileInputStream input22 = new FileInputStream(file22.getAbsolutePath());
            Image exit = new Image(input22, 40, 40, true, true);
            images.put("Exit", exit);
            
            File file23 = new File("src/resources/switch.png");
            FileInputStream input23 = new FileInputStream(file23.getAbsolutePath());
            Image pressure_plate = new Image(input23, 25, 25, true, true);
            images.put("Switch", pressure_plate);

        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public Image getImage(String key){
        return images.get(key);
    }
}
