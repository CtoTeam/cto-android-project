package cto.team.certificatechecker.models;

import java.util.Date;

public class SoldierDetails implements NetworkModel{

	public int SoldierID;
	public String Name;
	public String CertID;
	public String ExpirationDate;
	public CarPermission[] CarPermissions;
}
