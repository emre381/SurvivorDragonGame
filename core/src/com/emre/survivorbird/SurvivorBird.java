package com.emre.survivorbird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;

import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class SurvivorBird extends ApplicationAdapter {
	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture enemy1;
	Texture enemy2;
	Texture enemy3;

	float birdX =0;
	float birdY =0;
	int gameState = 0;
	float velocity =0;
	float gravity =1f;
	float enemyVelocity = 10;
	Random random;
	int score =0;
	int scoreEnemy =0;
	BitmapFont font;
	BitmapFont font2;


	Circle birdCircle;
	ShapeRenderer shapeRenderer;



	int numberOfEnemeies=4;
	float [] enemyX =new float[ numberOfEnemeies];
	float[] enemyOffset = new float[numberOfEnemeies];
	float[] enemyOffSet2 = new float[numberOfEnemeies];
	float[] enemyOffSet3 =  new  float[numberOfEnemeies];
	float distance = 0;
	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;


	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy1 = new Texture("enemy.png");
		enemy2 = new Texture("enemy.png");
		enemy3 = new Texture("enemy.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();


		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight() / 3;

		shapeRenderer = new ShapeRenderer();


		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemeies];
		enemyCircles2 = new Circle[numberOfEnemeies];
		enemyCircles3 = new Circle[numberOfEnemeies];


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(9);

		font2 = new BitmapFont();
		font2.setColor(Color.BLACK);
		font2.getData().setScale(6);


		for (int i = 0; i<numberOfEnemeies; i++) {


			enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;


			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();

		}




	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if (gameState ==1) {



			if (enemyX[scoreEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
				score++;

				if (scoreEnemy < numberOfEnemeies - 1) {
					scoreEnemy++;
				} else {
					scoreEnemy = 0;
				}

			}



			if (Gdx.input.justTouched()) {

				velocity = -20;

			}


			for (int i = 0; i < numberOfEnemeies; i++) {


				if (enemyX[i] < Gdx.graphics.getWidth() / 15) {
					enemyX[i] = enemyX[i] + numberOfEnemeies * distance;

					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);


				} else {
					enemyX[i] = enemyX[i] - enemyVelocity;
				}


				batch.draw(enemy1,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffset[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);


				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffset[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 60);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 60);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 60);


			}



			if (birdY > 0) {
				velocity = velocity + gravity;
				birdY = birdY - velocity;
			} else {
				gameState = 2;
			}


		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			font2.draw(batch,"Game Over! Tap To Play Again!",500,Gdx.graphics.getHeight() / 2 +30);

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;


				for (int i = 0; i<numberOfEnemeies; i++) {


					enemyOffset[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - enemy1.getWidth() / 2 + i * distance;


					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				velocity = 0;
				scoreEnemy = 0;
				score = 0;

			}
		}


		batch.draw(bird,birdX, birdY, Gdx.graphics.getWidth() / 15,Gdx.graphics.getHeight() / 10);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		birdCircle.set(birdX +Gdx.graphics.getWidth() / 30 ,birdY + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);




		for ( int i = 0; i < numberOfEnemeies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

			if (Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {
				gameState = 2;
			}
		}

		//shapeRenderer.end();

	}

	@Override
	public void dispose () {

	}
}
