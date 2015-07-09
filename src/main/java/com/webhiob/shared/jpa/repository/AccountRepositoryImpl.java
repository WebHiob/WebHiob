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

package com.webhiob.shared.jpa.repository;

import com.webhiob.shared.jpa.dao.JpaDao;
import com.webhiob.shared.jpa.entity.Account;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class AccountRepositoryImpl extends JpaDao<Account, String> implements AccountRepository
{
    public AccountRepositoryImpl()
    {
	super(Account.class);
    }

    @Override
    @Transactional
    public void init()
    {
	int dailyLimit = 2000;
	insertAccount("victim", "Ela", dailyLimit);
	insertAccount("hacker", "Adam", dailyLimit);
    }

    private void insertAccount(String login, String name, int dailyLimit)
    {
	Account account = new Account();
	account.setLogin(login);
	account.setName(name);
	account.setDailyLimit(dailyLimit);

	insert(account);
    }
}
