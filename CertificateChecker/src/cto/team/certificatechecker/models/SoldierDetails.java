package cto.team.certificatechecker.models;

import java.util.Date;

public class SoldierDetails implements NetworkModel{

	public int SoldierId;
	public String Name;
	public String CertID;
	public Date ExpirationDate;
	public CarPermission[] Permissions;
}
