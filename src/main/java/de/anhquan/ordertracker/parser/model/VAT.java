package de.anhquan.ordertracker.parser.model;

public enum VAT {
	DE_19,
	DE_7;
	
    VAT()
    {
    }

    public int value()
    {
    	if (this == DE_19)
    		return 19;
    	else
    		return 7;
    }

}
