package org.hsweb.printer.utils;

import javax.sound.sampled.*;
import java.io.IOException;
import java.io.InputStream;

public class MusicPlayer {
    public static volatile boolean play=false;

    public static void playSystem(String fileName,boolean... b){
        play(ClassLoader.getSystemClassLoader().getResourceAsStream(String.format("alarm_%s.wav",fileName)),b);
    }



    // 播放
    public static void play(InputStream resourceAsStream,boolean... b) {

        try {
            // 取得文件输入流

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(resourceAsStream);

            AudioFormat audioFormat = audioInputStream.getFormat();
            // 转换mp3文件编码
            if (audioFormat.getEncoding() != AudioFormat.Encoding.PCM_SIGNED) {
                audioFormat = new AudioFormat(AudioFormat.Encoding.PCM_SIGNED,
                        audioFormat.getSampleRate(), 16, audioFormat
                        .getChannels(), audioFormat.getChannels() * 2,
                        audioFormat.getSampleRate(), false);
                audioInputStream = AudioSystem.getAudioInputStream(audioFormat,
                        audioInputStream);
            }

            threadPlay(audioInputStream,b);
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void threadPlay(AudioInputStream audioInputStream,boolean... b) {
        new Thread(()->{
            boolean played=false;
            while (!played) {
                if(play){
                    try {
                        Thread.sleep(500L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    if(b!=null&&b.length>0&&b[0]){
                        break;
                    }
                    continue;
                }
                play=true;

                SourceDataLine sourceDataLine = null;
                try {
                    // 打开输出设备
                    DataLine.Info dataLineInfo = new DataLine.Info(
                            SourceDataLine.class, audioInputStream.getFormat(),
                            AudioSystem.NOT_SPECIFIED);
                    sourceDataLine = (SourceDataLine) AudioSystem.getLine(dataLineInfo);
                    sourceDataLine.open(audioInputStream.getFormat());
                    sourceDataLine.start();

                    byte tempBuffer[] = new byte[320];
                    int cnt;
                    // 读取数据到缓存数据
                    while ((cnt = audioInputStream.read(tempBuffer, 0,
                            tempBuffer.length)) != -1) {
                        if (cnt > 0) {
                            // 写入缓存数据
                            sourceDataLine.write(tempBuffer, 0, cnt);
                        }
                    }
                    // Block等待临时数据被输出为空

                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    play=false;
                    played=true;
                    if (sourceDataLine != null) {
                        sourceDataLine.drain();
                        sourceDataLine.close();
                    }
                }
            }
            if (audioInputStream != null) {
                try {
                    audioInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
