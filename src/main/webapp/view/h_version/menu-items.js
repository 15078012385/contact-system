const menuItems = [
    {
        id: 'dashboard',
        title: '仪表盘',
        icon: 'el-icon-data-line',
        url: 'dashboard.html',
        roles: ['admin', 'user']
    },
    {
        id: 'user',
        title: '用户管理',
        icon: 'el-icon-user',
        roles: ['admin'],
        submenu: [
            {
                id: 'userList',
                title: '用户列表',
                url: 'userList.html',
                roles: ['admin', 'manager', 'cnm']
            },
            {
                id: 'roleManagement',
                title: '角色管理',
                url: 'roleManagement.html',
                roles: ['admin']
            }
        ]
    },{
        "id": "favoriteContacts",
        "title": "收藏联系人",
        "icon": "el-icon-star-off",
        "url": "favorite-contacts.html",
        "roles": ["admin", "user"]
    },
    {
        "id": "contact",
        "title": "通讯录管理",
        "icon": "el-icon-phone",
        "url": "contact.html",
        "roles": ["admin", "user"]
    }
];

// 导出 menuItems
export default menuItems;