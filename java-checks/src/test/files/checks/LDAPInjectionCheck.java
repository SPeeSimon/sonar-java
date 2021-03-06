import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

class A {

  String[] requestedAttrsField;
  public User lookupUser(String username, String base, String[] requestedAttrs) {

    DirContext dctx = new InitialDirContext(env);
    String[] requestedAttrsLocal = new String[12];
    SearchControls sc = new SearchControls();
    sc.setReturningAttributes(requestedAttrs);  // Noncompliant [[sc=31;ec=45]] {{Make sure that "requestedAttrs" is sanitized before use in this LDAP request.}}
    sc.setSearchScope(SearchControls.SUBTREE_SCOPE);

    String filter = "(&(objectClass=user)(sAMAccountName=" + username + "))";

    NamingEnumeration results = dctx.search(base,  // Noncompliant [[sc=45;ec=49]] {{Make sure that "base" is sanitized before use in this LDAP request.}}
        // parameter concatenated directly into string
        filter,  // Noncompliant {{Make sure that "username" is sanitized before use in this LDAP request.}}
        sc);
    results = dctx.search(base+"",  // Noncompliant {{Make sure that "base" is sanitized before use in this LDAP request.}}
        // parameter concatenated directly into string
        filter,  // Noncompliant {{Make sure that "username" is sanitized before use in this LDAP request.}}
        sc);
    sc.setReturningAttributes(requestedAttrsField);  // Noncompliant {{Make sure that "requestedAttrsField" is sanitized before use in this LDAP request.}}
    sc.setReturningAttributes(new String[]{" ", username});  // Noncompliant {{Make sure that "username" is sanitized before use in this LDAP request.}}
    sc.setReturningAttributes(new String[]{" ", " Foo"});  // compliant
    sc.setReturningAttributes(requestedAttrsLocal);  // Noncompliant {{Make sure that "requestedAttrsLocal" is sanitized before use in this LDAP request.}}

    javax.naming.directory.InitialDirContext idc = org.owasp.benchmark.helpers.Utils.getInitialDirContext();
    idc.search("name", filter, new javax.naming.directory.SearchControls()); // Noncompliant {{Make sure that "username" is sanitized before use in this LDAP request.}}

    idc.search("name", getAttributes(), null); // Compliant
  }

  private static Attributes getAttributes() {
    return null;
  }
}
