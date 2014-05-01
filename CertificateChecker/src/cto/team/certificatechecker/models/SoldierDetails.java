package cto.team.certificatechecker.models;


public class SoldierDetails implements NetworkModel{

	public int SoldierID;
	public String Name;
	public String CertID;
	public String ExpirationDate;
	public int IsStolen;
	public CarPermission[] CarPermissions;
}
