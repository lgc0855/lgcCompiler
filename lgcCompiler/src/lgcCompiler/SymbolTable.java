package lgcCompiler;

public class SymbolTable {
	public int tableptr ; //当前符号表的项指针。
    public static final int tableMax = 100;         //符号表的大小
    public static final int symMax = 10;            //符号的最大长度
    public static final int addrMax = 1000000;      //最大允许的数值
    public static final int levMax = 3;             //最大允许过程嵌套声明层数[0,levmax]
    public static final int numMax = 14;            //number的最大位数
    public static boolean tableswitch;              //显示名字表与否
    public static final int constant = 0;           //常量
    public static final int variable = 1;           //变量
    public static final int procedure = 2;          //过程
    
    public static Item[] table = new Item[tableMax] ;
    
    public class Item{
        String name;                                             //名字
        int type;                                               //类型，const var or procedur
        int value;                                                 //数值，const使用
        int lev;                                                 //所处层，var和procedur使用
        int addr;                                                //地址，var和procedur使用
        int size;                                               //需要分配的数据区空间，仅procedure使用
		public Item() {
			super();
			name = "";
		}
    }
    
    //获得符号表某一项
    public Item getItem(int i){
    	if(table[i] == null ){
    		table[i] = new Item();
    	}
    	return table[i] ;
    }
    
    //填表操作
    //@param sym 要填入的符号
    //@param type 填入符号的类型
    //@param lev 填入符号的层次
    //@param dx 填入符号的偏移量
    public void enter(Symbol sym, int type, int lev, int dx) {
    	tableptr++ ;
    	Item item = getItem(tableptr);
    	item.name = sym.getId() ;
    	item.type = type ;
    	switch(type){
    	case constant :
    		item.value = sym.getNum();
    		break;
    	case variable :
    		item.lev = lev ;
    		item.addr = dx ;
    		break;
    	case procedure :
    		item.lev = lev ;
    		item.addr = dx;
    		break;
    	}
    }
    
    
    //查找名字为name的符号从后往前查找。
    public int searchSymbol(String name){
    	int i = tableptr ;
    	while(i>0){
    		if(table[i].name.equals(name)){
    			return i ;
    		}
    		i--;
    	}
    	return 0 ;
    }
    
    /*
     * String name;                                             //名字
        int type;                                               //类型，const var or procedur
        int value;                                                 //数值，const使用
        int lev;                                                 //所处层，var和procedur使用
        int addr;                                                //地址，var和procedur使用
        int size;                                               //需要分配的数据区空间，仅procedure使用
     */
    
    public void printTable(){
    	if(!tableswitch){
    		return ;
    	}
    	int i = tableptr ;
    	int type ;
    	Item item = null  ;
    	while(i>0){
    		try{
    			item = table[i];
        		type = item.type ;
        		switch(type){
        		case constant :
        			System.out.println("name : " + item.name + "  type : constant   value : " + item.value );
        			break;
        		case variable :
        			System.out.println("name : " + item.name + "  type : variable   lev : " + item.lev 
        					+"   addr : "+item.addr );
        			break;
        		case procedure :
        			System.out.println("name : " + item.name + "  type : procedure   lev : " + item.lev 
        					+"   addr : "+item.addr + "   size : "+ item.size);
        			break;
        		}
        		i--;
    		}catch(Exception e){
    			System.out.println("显示符号表出错！");
    			return;
    		}
    	}
    }
}
