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

- PasswordEncoder  
  PasswordEncode에는 다양한 전략이 있다. -> noop, bcrypt, sha256, pbkdf2, scrypt 등등...  
  스프링 시큐리티는 DelegatingPasswordEncoder(Delegate: 대표(자))라는 것을 이용해 이런 다양한 종류들의 PasswordEncoder중에서  
  어떤 것을 선택했는지 알려주기 위해서 {bcrypt}xxx.... 와 같은 방식으로 표현한다.  
  그래서 어떤 암호는 bcrypt로 암호화되고 다른 암호는 sha256으로 되었다고 하더라도 DelegatingPasswordEncoder는 둘 다 지원할 수 있다.  
  기본 설정은 bcrypt 방식  
![password_encoder](https://github.com/jiseongTak/spring-security-practice/assets/98400407/370027d5-0b5d-41dc-92b7-b41dae1bea38)  
PasswordEncoderFactories 클래스에서 확인이 가능  
  
- Security Filter
  Spring Security의 동작은 사실상 Filter로 동작한다고 해도 무방.  
  다양한 필터들이 존재하는데 이 Filter들은 각자 다른 기능을 하고 있다. 이런 Filter들은 제외할 수도 있고 추가할 수도 있다.  
  필터에 동작하는 순서를 정해줘서 원하는 대로 동작할 수 있다.  
  필터들은 GenericFilterBean을 상속하고 있고 GenericFilterBean는 Filter를 구현하고 있다.  
  Filter는 요청이나 응답 또는 둘 다에 대해 필터링 작업을 수행한다. doFilter 메서드에서 필터링을 수행, 즉 Filter는 doFilter를 구현해야 한다.  
  다양한 필터들과 그 필터들의 동작 순서는 FilterChainProxy 클래스에서 doFilterInternal에서 break point를 걸고 디버깅하면 확인할 수 있다.  
  <img src="https://github.com/jiseongTak/spring-security-practice/assets/98400407/c98a391a-f049-420a-be7f-19cb06e3fe25" width="400" height="310"/>  
  총 14개의 필터가 적용되고 있는 것을 확인할 수 있음.  
  FilterOrderRegistration에서 필터의 순서를 확인할 수 있다. 초기 step이 100이고 100씩 증가한다. 사이사이에 커스텀 필터를 넣을 수 있다.  
  
