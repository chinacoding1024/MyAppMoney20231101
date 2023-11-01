package com.stu.security.filter;

import cn.hutool.core.util.StrUtil;
import com.stu.base.result.R;
import com.stu.security.utils.JwtUtils;

import com.stu.security.utils.ResponseUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.lang.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.util.StringUtils;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/******************************
 * 用途说明: Basic身份认证过滤器
 * 作者姓名: 公众号:小明的学习圈子  https://www.stucoding.com/
 * 创建时间: 2022-07-27 23:16
 ******************************/
public class JwtAuthenticationFilter extends BasicAuthenticationFilter {

    @Autowired
    JwtUtils jwtUtils;
    @Autowired
    JwtUtils tokenManager;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    UserDetailsService userDetailService;

//    @Autowired
//    private IndexService indexService;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager,
                                   JwtUtils tokenManager,
                                   RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.tokenManager = tokenManager;
        this.redisTemplate = redisTemplate;

    }

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//        if (request.getRequestURI().indexOf("/home") >= 0 ) {
//            chain.doFilter(request, response);
//            return;
//        }
        String jwt = request.getHeader(jwtUtils.getHeader());
        System.out.println("jwt============================" + jwt);

        if (StrUtil.isBlankOrUndefined(jwt)) {
            chain.doFilter(request, response);
            return;
        }

        Claims claim = jwtUtils.getClaimByToken(jwt);
        if (claim == null) {
            throw new JwtException("token 异常");
        }
        if (jwtUtils.isTokenExpired(claim)) {
            throw new JwtException("token已过期");
        }

        String username = claim.getSubject();
        // 获取用户的权限等信息

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = null;
        try {
//            usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, null, userDetailService.getUserAuthority(sysUser.getId()));
            usernamePasswordAuthenticationToken = getAuthentication(request);
        } catch (Exception e) {
            ResponseUtil.out(response, R.error());
        }
        if (usernamePasswordAuthenticationToken != null) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        } else {
            ResponseUtil.out(response, R.error());
        }
        chain.doFilter(request, response);
    }

    /***********************************
     * 用途说明:从request获取token，根据token获取权限列表
     * 返回值说明:
     * @return org.springframework.security.authentication.UsernamePasswordAuthenticationToken
     ***********************************/
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(jwtUtils.getHeader());

        if (token != null && !"".equals(token.trim())) {
            Claims claims = jwtUtils.getClaimByToken(token);
            String userName = claims.getSubject();
            if (null == redisTemplate.opsForValue().get(userName)) {
                return null;//indexService.getUserInfo(userName);
            }

            List<String> permissionValueList = (List<String>) redisTemplate.opsForValue().get(userName);
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            if (Collections.isEmpty(permissionValueList)) {
                return null;
            }
            for (String permissionValue : permissionValueList) {
                if (StringUtils.isEmpty(permissionValue)) {
                    continue;
                }
                SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(permissionValue);
                authorities.add(simpleGrantedAuthority);
            }

            return new UsernamePasswordAuthenticationToken(userName, token, authorities);
        }
        return null;
    }
}
