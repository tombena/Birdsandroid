package fr.sio.ecp.federatedbirds.model;

import java.io.Serializable;

/**
 * Created by Michaël on 24/11/2015.
 */
public class User implements Serializable {

    public long id;
    public String login;
    public String avatar;
    public String coverPicture;
    public String email;

}
