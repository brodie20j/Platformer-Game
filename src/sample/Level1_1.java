package sample;

import java.util.List;

/**
 * Created by jonathanbrodie on 1/2/15.
 */
public class Level1_1 extends Level {
    public Level1_1() {
        this.setLevelName("1-1");
        Block block;
        GrassBlock grass;
        DirtBlock dirt;
        /*for (int i=0; i<50; i++) {
            grass=new GrassBlock(64*i, 432);
            this.addBlockToList(grass);
            dirt=new DirtBlock(64*i, 496);
            this.addBlockToList(dirt);
        }*/
        drawGroundRectangle(0,432,3200,496);
        block=new CoinBlock(64*16,240);
        this.addBlockToList(block);
        block=new MushroomBlock(64*17,48);
        this.addBlockToList(block);
        block=new CoinBlock(64*18,240);
        this.addBlockToList(block);


        for (int i=47; i<50; i++) {
            block=new SolidBlock(i*64,368);
            this.addBlockToList(block);
        }
        for (int i=48; i<50; i++) {
            block=new SolidBlock(i*64,304);
            this.addBlockToList(block);
        }
        for (int i=49; i<50; i++) {
            block=new SolidBlock(i*64,240);
            this.addBlockToList(block);
        }
        for (int i=53; i<68; i++) {
            grass=new GrassBlock(64*i, 432);
            this.addBlockToList(grass);
            dirt=new DirtBlock(64*i, 496);
            this.addBlockToList(dirt);
        }
        for (int i=53; i<56; i++) {
            block=new SolidBlock(i*64,368);
            this.addBlockToList(block);
        }
        for (int i=53; i<55; i++) {
            block=new SolidBlock(i*64,304);
            this.addBlockToList(block);
        }
        for (int i=53; i<54; i++) {
            block=new SolidBlock(i*64,240);
            this.addBlockToList(block);
        }
        for (int i=68; i<78; i++) {
            if (i==68) block=new MushroomBlock(i*64,240);
            else block=new CoinBlock(i*64,48);
            this.addBlockToList(block);
        }

        drawGroundRectangle(64*78,432, 6400,496);
        drawGroundRectangle(6656,432,7168,496);

        drawGroundRectangle(7168,0,7360,512);

        //for (int i=)

        Goomba goomba=new Goomba(32*17,336);
        this.addEnemyToList(goomba);
        goomba=new Goomba(32*30,336);
        this.addEnemyToList(goomba);
        goomba=new Goomba(64*20,336);
        this.addEnemyToList(goomba);
        goomba=new Goomba(64*24,336);
        this.addEnemyToList(goomba);
        Koopa koopa=new Koopa(32*17, 336);
        this.addEnemyToList(koopa);

        block=new EndBlock(7040,240);
        this.addBlockToList(block);

        this.setBackgroundString("background-grassland.png");

    }


}
