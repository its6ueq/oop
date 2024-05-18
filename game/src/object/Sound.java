package object;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {

    String soundURL[] = {
            "resource/audio/StartGame.wav",
            "resource/audio/EndGame.wav",
            "resource/audio/BulletHit.wav",
            "resource/audio/BulletFired.wav",
            "resource/audio/EnermyTankDestroyed.wav",
            "resource/audio/AllyTankDestroyed.wav"
    };

    Clip[] clip = new Clip[5];

    public Sound(){
        for(int i = 0; i <= 5; i++) {
            try {
                AudioInputStream ais = AudioSystem.getAudioInputStream (new File (soundURL[i]).getAbsoluteFile ());
                clip[i] = AudioSystem.getClip ();
                clip[i].open (ais);
            } catch (Exception e) {
            }
        }
    }

    public void play(int i){
        clip[i].setFramePosition(0);
        clip[i].start();
    }

    public void loop(int i){
        clip[i].loop(Clip.LOOP_CONTINUOUSLY);

    }

    public void stop(int i){
        clip[i].stop();
    }

    public void playMusic(int i){
        play(i);
    }

    public void stopMusic(int i){
        stop(i);
    }

    public void playSE(int i){
        play (i);
    }

}