/***************************************************************************
 * Product made by Quang Dat *
 **************************************************************************/
package com.vtc.event.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.vtc.event.common.Constant;

/**
 * Author : Dat Le Quang
 * Email: Quangdat0993@gmail.com
 * Jun 12, 2019
 */
@Configuration
@EnableAuthorizationServer
public class Oauth2AuthorizationServer extends AuthorizationServerConfigurerAdapter {
    
    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;
    
    @Override
    public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }
    
    @Override
    public void configure(ClientDetailsServiceConfigurer clients)
            throws Exception {
        clients.inMemory()
                .withClient(Constant.CLIENT_ID)
                .secret(Constant.CLIENT_SECRET)
                .authorizedGrantTypes(
                        "password")
                .scopes("read")
                .accessTokenValiditySeconds(60*60*48);
    }
    
    @Bean
    public JwtTokenStore tokenStore() {
        JwtTokenStore store = new JwtTokenStore(tokenEnhancer());
        return store;
    }
    
    @Override
    public void configure(
            AuthorizationServerEndpointsConfigurer endpoints)
            throws Exception {

        endpoints
        .tokenStore(tokenStore()).tokenEnhancer(tokenEnhancer())
                .pathMapping("/oauth/token", "/lgi")
                .authenticationManager(authenticationManager);
    }
    
    @Bean
    public JwtAccessTokenConverter tokenEnhancer(){
        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("my-key.jks"), "vtcmobile".toCharArray());
        JwtAccessTokenConverter converter = new MyTokenEnhancer();
        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("keyAlias"));
        return converter;
    }

}
