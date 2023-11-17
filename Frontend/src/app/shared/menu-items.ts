import { Injectable } from "@angular/core";

export interface Menu {
    state: string;
    name: string;
    type: string;
    icon: string;
    role: string[];
}

const MENUITEMS = [
    { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: ['user', 'admin'] },
    { state: 'product', name: 'Manage  Product', type: 'link', icon: 'inventory_2', role: ['admin'] },
    { state: 'category', name: 'Manage Category', type: 'link', icon: 'category', role: ['admin'] },
    { state: 'bill', name: 'View Bill', type: 'link', icon: 'backup_table', role: ['admin', 'user'] },
    { state: 'user', name: 'Manager User', type: 'link', icon: 'people', role: ['admin'] },
    { state: 'order', name: 'Manager Order', type: 'link', icon: 'backup_table', role: ['admin','user'] },
]

@Injectable()
export class MenuItems {
    getMenuItem(): Menu[] {
        return MENUITEMS;
    }
}
