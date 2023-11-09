import { Injectable } from "@angular/core";

export interface Menu{
    state: string;
    name:string;
    type:string;
    icon:string;
    role:string;
}

const MENUITEMS = [
    {state:'product', name:'Manage  Product',type:'link', icon:'inventory_2',role:'manager'},
    {state:'category' , name:'Manage Category', type:'link', icon:'category', role:'manager'},
]

@Injectable()
export class MenuItems{
    getMenuItem():Menu[]{
        return MENUITEMS;
    }
}
