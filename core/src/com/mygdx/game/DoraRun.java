package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Rectangle;


import java.util.ArrayList;
import java.util.Random;

public class DoraRun extends Game {
	SpriteBatch batch;
	//texture is used to put images
	Texture background;
	Texture dizzy;
	Texture[] dora;//images of dora running
	int doraState=0;// in which state she is in
	int pause=0; //the gap of time between the change in images
	float gravity=0.35f;
	float velocity=10;
	int doraY=0;
	Random random;
	Rectangle characterRectangle;
	int score=0;
	BitmapFont font;
	int gameState=0;
	private Music music_level1;
	private Music musicJump;
	private Music musicBomb;
	private Music musicCoin;

	//for coins
	ArrayList <Integer> coinXs= new ArrayList<Integer>();
	ArrayList <Integer> coinYs= new ArrayList<Integer>();
	ArrayList <Rectangle> coinRectangles = new ArrayList<Rectangle>();
	Texture coin;// what the coin would look like
	int coinCount;//spacing between the coins

	//for bombs
	ArrayList <Integer> bombXs= new ArrayList<Integer>();
	ArrayList <Integer> bombYs= new ArrayList<Integer>();
	ArrayList <Rectangle> bombRectangles = new ArrayList<Rectangle>();
	Texture bomb;// what the coin would look like
	int bombCount;//spacing between the coins
//	private Object mainMenu;


	@Override
	public void create () {

		//open up for the first time
		batch = new SpriteBatch(); //putting something on the screen
		background= new Texture("bg.png");
		dizzy= new Texture("dizzy-1.png");
		dora = new Texture[4];//dora has in total 4 images stand, run stop, DED
		dora[0]= new Texture("frame-1.png");
		dora[1]= new Texture("frame-2.png");
		dora[2]= new Texture("frame-3.png");
		dora[3]= new Texture("frame-4.png");
		doraY=Gdx.graphics.getHeight(); //where she is going to be in the screen
		coin = new Texture("coin.png");
		bomb = new Texture("bomb.png");
		random = new Random();
		music_level1 = Gdx.audio.newMusic(Gdx.files.internal("level1.ogg"));
		musicJump = Gdx.audio.newMusic(Gdx.files.internal("jump.mp3"));
		musicBomb = Gdx.audio.newMusic(Gdx.files.internal("lost.mp3"));
		musicCoin = Gdx.audio.newMusic(Gdx.files.internal("coin.mp3"));
		music_level1.setLooping(true);
		musicBomb.setLooping(false);
		music_level1.play();


		//showing the score
		font = new BitmapFont();
		font.setColor(Color.WHITE);//font color
		font.getData().setScale(10);

		setScreen(new mainMenu());


	}
public void makeCoin(){
		float height = random.nextFloat()*Gdx.graphics.getHeight()-1;//random generates 0-9 val
		coinYs.add((int)height);
		coinXs.add(Gdx.graphics.getWidth());//x is always constant
	}

	public void makeBomb(){
		float height = random.nextFloat()*Gdx.graphics.getHeight()-1;//random generates 0-9 val
		bombYs.add((int)height);
		bombXs.add(Gdx.graphics.getWidth());//x is always constant
	}
	@Override
	public void render () {
		//keeps on rendering items untill the app is open
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());


		//this basically tells us if the game is going on, about to start
		//or you DEAD
		if(gameState==1){
			//game is on
			music_level1.play();
			//bombs
			if(bombCount<250){
				bombCount++;
			}else{
				bombCount=0;
				makeBomb(); //putting a another coin

			}
			bombRectangles.clear();
			for(int i =0; i< bombXs.size();i++){
				batch.draw(bomb,bombXs.get(i),bombYs.get(i));
				bombXs.set(i,bombXs.get(i)-8);//coin going to the left
				bombRectangles.add(new Rectangle(bombXs.get(i), bombYs.get(i), bomb.getWidth(),bomb.getHeight()));
			}



//coins
			if(coinCount<100){
				coinCount++;
			}else{
				coinCount=0;
				makeCoin(); //putting a another coin

			}
			coinRectangles.clear();//clear out the whole arraylist so that it doesnt cointain
			//all the shown coin information evrytime it passes
			for(int i =0; i< coinXs.size();i++){
				batch.draw(coin,coinXs.get(i),coinYs.get(i));
				coinXs.set(i,coinXs.get(i)-6);//coin going to the left
				coinRectangles.add(new Rectangle(coinXs.get(i), coinYs.get(i), coin.getWidth(),coin.getHeight()));
			}



			if(Gdx.input.justTouched()){
				//user touched the screen
				velocity=-10;//because minus minus e plus hbe dora y er
				musicJump.play();
			}

			if(pause <8){
				pause++;
			}else {
				pause=0;
				if (doraState < 3) {
					doraState++;
				} else {
					doraState = 0;
				}

			}

			velocity = velocity + gravity;
			doraY -= velocity; //taking him down/ falling down

			if(doraY<=0){
				//if she is at the bottom of the screen
				doraY=0;
			}
		}else if (gameState==0){
			//waiting to start





			if(Gdx.input.justTouched()){
				gameState=1;
				music_level1.play();
			}


		}else if (gameState==2){
			//GAME OVERRRRRRRR DEDDDD
//			musicBomb.stop();

			if(Gdx.input.justTouched()){
				gameState=1;
				doraY=Gdx.graphics.getHeight()/2;
				score=0;
				velocity=0;
				coinXs.clear();;
				coinYs.clear();
				coinRectangles.clear();
				coinCount=0;
				bombXs.clear();;
				bombYs.clear();
				bombRectangles.clear();
				bombCount=0;
				if(musicBomb.isPlaying()){
				    musicBomb.stop();
                }
			}//starting the game again
		}


if(gameState==2){
	batch.draw(dizzy, Gdx.graphics.getWidth()/2-dora[doraState].getWidth()/2, doraY);


}else{
	batch.draw(dora[doraState],Gdx.graphics.getWidth()/2-dora[doraState].getWidth()/2, doraY);

}

		//order is really imp

		//centering the character to the screen          minusing becuase it would start from center and wouldnt look centered anymore
		characterRectangle= new Rectangle(Gdx.graphics.getWidth()/2-dora[doraState].getWidth()/2,doraY, dora[doraState].getWidth(),dora[doraState].getHeight());
		//		finding out where dora is her location

//for loop to get through all our coins
		for(int i=0; i< coinRectangles.size();i++){
			if(Intersector.overlaps(characterRectangle,coinRectangles.get(i))){
                //intersector used for if two things are overlaping
                Gdx.app.log("coin","Collision");
				score++;
				musicCoin.play();
				coinRectangles.remove(i);
				coinXs.remove(i);
				coinYs.remove(i);
				//these are written to remove the coin after it collides so that
				//collison is only one time
				break;
			}


		}

		//for loop to get through all our bombs
		for(int i=0; i< bombRectangles.size();i++){
			if(Intersector.overlaps(characterRectangle,bombRectangles.get(i))) {
                //intersector used for if two things are overlaping
                Gdx.app.log("bomb", "Collision");
                musicBomb.play();
                music_level1.stop();
                gameState=2;

            }
		}


		font.draw(batch, String.valueOf(score),100,200 );
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();

	}
}
