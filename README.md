WebSecurityConfigurerAdapter가 Deprecated 되어서 다른 것을 이용해서 구현  
<https://docs.spring.io/spring-security/reference/5.8/migration/servlet/config.html#_stop_using_websecurityconfigureradapter>

Spring Security 내부 구조  
SecurityContextHolder -> SecurityContext -> Authentication -> Principal & GrantAuthority  

- SecurityContextHolder  
SecurityContextHolder는 SecurityContext를 제공하는 static 메소드(getContext)를 지원  

- SecurityContext  
SecurityContext 는 접근 주체와 인증에 대한 정보를 담고 있는 Context 즉, Authentication 을 담고 있다.

- Authentication  
Principal과 GrantAuthority를 제공, 인증이 이루어 지면 해당 Athentication이 저장된다.

- Principal  
유저에 해당하는 정보, 대부분의 경우 Principal로 UserDetails를 반환

- GrantAuthority  
ROLE_ADMIN, ROLE_USER등 Principal이 가지고 있는 권한을 나타낸다. prefix로 ROLE_ 이 붙는다.  
인증 이후에 인가를 할 때 사용, 권한은 여러개 일수 있기 때문에 Collection<GrantedAuthority>형태로 제공  
ex) ROLE_DEVELOPER, ROLE_ADMIN  

