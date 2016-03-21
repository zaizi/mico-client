package org.zaizi.mico.client.model.face;

public class FaceFragment {

	private Long x;
    private Long y;
    private Long width;
    private Long height;
    
    public FaceFragment(Long x, Long y, Long width, Long height){
    	this.x = x;
    	this.y = y;
    	this.width = width;
    	this.height = height;
    }
    
    public Long getX(){
    	return x;
    }
    
    public Long getY(){
    	return y;
    }
    
    public Long getWidth(){
    	return width;
    }
    
    public Long getHeight(){
    	return height;
    }
}
