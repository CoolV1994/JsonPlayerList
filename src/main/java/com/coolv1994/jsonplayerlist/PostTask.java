package com.coolv1994.jsonplayerlist;

/**
 *
 * @author Vinnie
 */
public class PostTask implements Runnable {

    @Override
    public void run() {
        Utils.postPlayerList();
    }
}
