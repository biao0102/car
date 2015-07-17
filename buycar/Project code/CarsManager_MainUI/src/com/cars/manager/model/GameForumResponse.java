package com.cars.manager.model;

public class GameForumResponse extends BaseBean
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -7932876163841372897L;

	private GameForumList result;

	public GameForumResponse()
	{

	}

	public void setResult(GameForumList result)
	{
		this.result = result;
	}

	public GameForumList getResult()
	{
		if (result == null)
		{
			result = new GameForumList();
		}
		return result;
	}
}
