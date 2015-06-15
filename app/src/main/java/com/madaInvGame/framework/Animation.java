package com.madaInvGame.framework;

import com.kilobolt.framework.Image;
import java.util.ArrayList;

public class Animation {

	public ArrayList<AnimFrame> frames;
	public int currentFrame;
	public long animTime;
	public long totalDuration;
	public boolean ended=false;

	public Animation() {
		// TODO Auto-generated constructor stub
		frames = new ArrayList<AnimFrame>();
		totalDuration = 0;
		synchronized(this){
			animTime=0;
			currentFrame=0;
		}
	}

	public synchronized void addFrame(Image image, long duration){
		totalDuration += duration;
		frames.add(new AnimFrame(image, totalDuration));
	}
	
	public synchronized Image getImage(){
		if(frames.size() == 0){
			return null;
		}else{
			return frames.get(currentFrame).image;
		}
	}
	
	public synchronized void update(long elapsedTime){
		if(frames.size()>1){
			animTime +=elapsedTime;
			if (animTime >= totalDuration) {
				animTime = animTime % totalDuration;
				currentFrame = 0;
			}
			while(animTime>frames.get(currentFrame).endTime){
				currentFrame++;
			}
		}
	}
}
