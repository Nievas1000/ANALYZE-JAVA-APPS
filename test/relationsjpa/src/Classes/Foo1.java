/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Classes;

import java.util.List;




public class Foo1 {
    
    
    
@OneToOne            
 private Foo2 foo2;

@OneToMany        
List<Foo3> foos3;


}
