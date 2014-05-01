package cto.team.certificatechecker.models;

import java.util.Date;

import com.google.gson.annotations.SerializedName;

public class SoldierDetails implements NetworkModel{

	public int SoldierID;
	public String Name;
	public String CertID;
	public String ExpirationDate;
	@SerializedName("IsValid")
	public boolean IsValidCert;
	public CarPermission[] CarPermissions;
}
