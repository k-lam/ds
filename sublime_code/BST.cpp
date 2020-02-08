class BinaryTreeNode{
    public:
    BinaryTreeNode *left;
    BinaryTreeNode *right;
    int value;
    void setValue(int value);
}

public *BinaryTreeNode deleteMin(BinaryTreeNode *&root){
    if(root->left){
        return deleteMin(root->left);
    }else{
        BinaryTreeNode *tmp = root;
        root = root->right;// 注意，这就是用C++不用C的原因，但参数一定要是*&，指针引用，这样就真会修改
        return tmp;
    }
}

public void removehelp(BinaryTreeNode *&root, int value){
    if(root == NULL){
        cout<<"not found"
    }else if(value > root.value){
        removehelp(root->right, value);
    }else if(value < root.value){
        removehelp(root->left, value);
    }else{
        BinaryTreeNode *tmp = root;
        if(root->left == NULL) root = root->right;
        else if(root->right == NULL) root = root->left;
        else{
            tmp = deleteMin(root->right);
            root->setValue(tmp->value);
        }
        delete tmp;
    }
}