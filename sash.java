public void update() {
    	
    	for (int i=0; i<workers.size(); i++ ){
    		SwingWorker<Void,Void> temp = workers.get(i);
    		temp.cancel(true);
    		
    	}

    	workers.clear();
    	
    	
    	for(int widx = 0; widx<WMAX ; widx++ ){

    		final int xmin = widx*width/WMAX;
    		final int xmax = (widx+1)*width/WMAX;

    		SwingWorker<Void,Void> worker = new SwingWorker<Void,Void>() {

    			@Override
    			public Void doInBackground() {
    				//System.out.println("Width: " + width + " Height:" + height);
    				for(int bsize = 64; bsize>0; bsize = bsize/2){
    					for (int x=xmin; x<=xmax; x+=bsize) {
    						for (int y=0; y<=height; y+=bsize) {
    							
    							CDouble c = getPointJittered(x, y, 0.1);
    							try{
    	    						int escapeIters = getEscapeIters(c);				
    	    						
    	    						for (int z=x; z<x+bsize && z<xmax; z++) {					
    									for (int a=y; a<=y+bsize && a<height; a+=1) {
    										
    											iters[z*height + a] = escapeIters;
    									}
    								}
    	    						
    	    						}catch (InterruptedException e){
    	    						 
    	    						}
    						}
    						//System.out.println("test");
    						
    					}
    					publish();
    				}
    				return null;

    			}
    			
    			@Override
    			public void done() {
    				fireModelChangedEvent();
    			}
    			
    			@Override
    			public void process(List<Void> chunks) {
    				fireModelChangedEvent();
    			}

    		};    	
    		//System.out.println("test");
    		workers.add(worker);
    		worker.execute();
    	}
}