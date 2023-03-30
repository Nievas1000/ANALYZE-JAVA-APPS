/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Classes;

import java.util.List;

public class Foo2 {

    @ManyToOne
    @JoinColumn(name = "university_id")
    @JoinColumn(name = "university_id")
    @JoinColumn(name = "university_id")
    private Foo3 foo3;

    @ManyToMany
    private List<Foo1> foo1s;

}
