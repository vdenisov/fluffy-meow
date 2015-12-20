/*
 * Fluffy Meow - Torrent RSS generator for TV series
 * Copyright (C) 2015 Victor Denisov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published
 * by the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 *  You should have received a copy of the GNU Affero General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.plukh.fluffymeow.shiro;

import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.RandomNumberGenerator;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.guice.web.ShiroWebModule;

import javax.servlet.ServletContext;

public class FluffyShiroModule extends ShiroWebModule {
    public FluffyShiroModule(ServletContext servletContext) {
        super(servletContext);
    }

    @Override
    protected void configureShiroWeb() {
        //Realm to interface our database
        bindRealm().to(MyBatisRealm.class).in(Scopes.SINGLETON);

        //Credentials-related stuff
        bind(CredentialsMatcher.class).to(HashedCredentialsMatcher.class).in(Scopes.SINGLETON);
        //Explicit binding to be picked up by shiro-guice's TypeListener
        //noinspection PointlessBinding
        bind(HashedCredentialsMatcher.class);
        bindConstant().annotatedWith(Names.named("shiro.hashAlgorithm")).to(Sha256Hash.ALGORITHM_NAME);
        bindConstant().annotatedWith(Names.named("shiro.hashIterations")).to(1024);
        bindConstant().annotatedWith(Names.named("shiro.storedCredentialsHexEncoded")).to(false);

        //Secure number generator, to be used throughout the application
        bind(RandomNumberGenerator.class).to(SecureRandomNumberGenerator.class).in(Scopes.SINGLETON);
    }
}
