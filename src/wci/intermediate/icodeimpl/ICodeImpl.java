package wci.intermediate.icodeimpl;

import wci.intermediate.ICode;
import wci.intermediate.ICodeNode;

public class ICodeImpl implements ICode
{
    private ICodeNode root;     // root node;

    /**
     * Set and return the root node.
     * @param node the node to set as root.
     * @return the root node
     */
    @Override
    public ICodeNode setRoot(ICodeNode node)
    {
        root = node;
        return root;
    }

    /**
     * Return the root node.
     * @return the root node
     */
    @Override
    public ICodeNode getRoot()
    {
        return root;
    }
}
