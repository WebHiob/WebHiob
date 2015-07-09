/***************************************************************************************************
 *
 *
 * This file is part of WebHiob, an Robert Rozmus utility.
 * WebHiob was created a as part of the master thesis by Robert Rozmus (student of Warsaw University of Technology, Institute of Computer Science)
 * Copyright (c) 2015 Robert Rozmus
 *
 * This program has got two licences:
 *  1. For non-commercial use - you can redistribute it and/or modify it under the terms of the
 *  		GNU General Public License version 3.0 (GPLv3);
 *
 *  2. For any commercial use (including payable academic lectures) - you must obtain the permission from the author
 *  (Robert Rozmus) to use it in these purposes
 *
 * @author <a href="mailto:robertrozmusjob@gmail.com">Robert Rozmus</a>
 */

package com.webhiob.shared.utils.token;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.codec.Hex;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class TokenUtils
{
    public static final String SEPARATOR = ":";
    public static final String MAGIC_KEY = "obfuscate";

    public static String createToken(UserDetails userDetails)
    {
	long expiresTime = calculateExpiresTime();

	return userDetails.getUsername() + SEPARATOR +
			expiresTime + SEPARATOR +
			TokenUtils.computeSignature(userDetails, expiresTime);
    }

    private static long calculateExpiresTime() {
	return System.currentTimeMillis() + 1000L * 15;
    }

    private static String computeSignature(UserDetails userDetails, long expires)
    {
	MessageDigest messageDigest = getMD5Digest();
	String signature = userDetails.getUsername() + SEPARATOR +
			expires +SEPARATOR +
			userDetails.getPassword() + SEPARATOR
			+ TokenUtils.MAGIC_KEY;
	byte[] signatureInBytes = signature.getBytes();
	byte[] digest = messageDigest.digest(signatureInBytes);
	return new String(Hex.encode(digest));
    }

    private static MessageDigest getMD5Digest() {
	MessageDigest messageDigest;
	try
	{
	    messageDigest = MessageDigest.getInstance("MD5");
	}
	catch (NoSuchAlgorithmException e)
	{
	    throw new IllegalStateException("MD5 algorithm is not available!");
	}
	return messageDigest;
    }

    public static String getUsernameFromToken(String authToken)
    {
	String username = null;
	if (authToken != null)
	{
	    String[] parts = authToken.split(SEPARATOR);
	    username = parts[0];
	}
	return username;
    }

    public static boolean validateToken(String authToken, UserDetails userDetails)
    {
	boolean valid = false;
	String[] parts = authToken.split(SEPARATOR);
	long expires = Long.parseLong(parts[1]);
	String signature = parts[2];

	if (expires >= System.currentTimeMillis())
	{
	    valid = signature.equals(TokenUtils.computeSignature(userDetails, expires));
	}

	return valid;
    }
}