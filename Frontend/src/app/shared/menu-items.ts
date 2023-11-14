import { Injectable } from "@angular/core";

export interface Menu {
    state: string;
    name: string;
    type: string;
    icon: string;
    role: string[];
}

const MENUITEMS = [
    { state: 'dashboard', name: 'Dashboard', type: 'link', icon: 'dashboard', role: ['manager', 'employee'] },
    { state: 'product', name: 'Manage  Product', type: 'link', icon: 'inventory_2', role: ['manager'] },
    { state: 'category', name: 'Manage Category', type: 'link', icon: 'category', role: ['manager'] },
    { state: 'bill', name: 'View Bill', type: 'link', icon: 'backup_table', role: ['manager', 'employee'] },
    { state: 'user', name: 'Manager User', type: 'link', icon: 'people', role: ['manager'] },
]

@Injectable()
export class MenuItems {
    getMenuItem(): Menu[] {
        return MENUITEMS;
    }
}
