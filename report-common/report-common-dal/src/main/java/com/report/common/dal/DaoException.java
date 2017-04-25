package com.report.common.dal;

public class DaoException extends RuntimeException
{
  private static final long serialVersionUID = 7662715726329519089L;

  public DaoException(String msg)
  {
    super(msg);
  }
}
