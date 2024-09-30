package wci.intermediate.icodeimpl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import wci.intermediate.*;

/**
 * <h1>ICodeNodeImpl</h1>
 *
 * <p>An implementation of a node of the intermediate code.</p>
 */
public class ICodeNodeImpl
    extends HashMap<ICodeKey, Object>
    implements ICodeNode
{
    private ICodeNodeType           type;       // node type
    private ICodeNode               parent;     // parent node
    private ArrayList<ICodeNode>    children;   // children array list
    private TypeSpec                typeSpec;   // data type specification

    /**
     * Constructor.
     * @param type the node type whose name will be the name of this node.
     */
    public ICodeNodeImpl(ICodeNodeType type)
    {
        this.type = type;
        this.parent = null;
        this.children = new ArrayList<ICodeNode>();
    }

    /**
     * Add a child node.
     * @param node the child node. Not added if null.
     * @return the child node.
     */
    @Override
    public ICodeNode addChild(ICodeNode node)
    {
        if (node != null) {
            children.add(node);
            ((ICodeNodeImpl) node).parent = this;    // this is hacky as fuck
        }

        return node;
    }

    /**
     * Set a node attribute.
     * @param key   the attribute key.
     * @param value the attribute value.
     */
    @Override
    public void setAttribute(ICodeKey key, Object value)
    {
        put(key, value);
    }

    /**
     * Get the value of a node attribute.
     * @param key the attribute key.
     * @return the attribute value.
     */
    @Override
    public Object getAttribute(ICodeKey key)
    {
        return get(key);
    }

    /**
     * Get the type of the node.
     * @return the type of the node.
     */
    @Override
    public ICodeNodeType getType()
    {
        return type;
    }

    /**
     * Get the parent of this node.
     * @return the parent of this node.
     */
    @Override
    public ICodeNode getParent()
    {
        return parent;
    }


    /**
     * Get the children of this node.
     * @return the ArrayList children of the node.
     */
    @Override
    public ArrayList<ICodeNode> getChildren()
    {
        return children;
    }

    /**
     * Make a copy of this node.
     * @return the copy.
     */
    @Override
    public ICodeNode copy()
    {
        ICodeNodeImpl copy =
            (ICodeNodeImpl) ICodeFactory.createICodeNode(type);

        Set<Map.Entry<ICodeKey, Object>> attributes = entrySet();
        Iterator<Map.Entry<ICodeKey, Object>> it = attributes.iterator();

        // Copy attributes
        while (it.hasNext()) {
            Map.Entry<ICodeKey, Object> attribute = it.next();
            copy.put(attribute.getKey(), attribute.getValue());
        }

        return copy;
    }

    /**
     * Set the type specification of this node.
     *
     * @param typeSpec the type specification of this node
     */
    @Override
    public void setTypeSpec(TypeSpec typeSpec)
    {
        this.typeSpec = typeSpec;
    }

    /**
     * Return the type specification of this node.
     *
     * @return the type specification.
     */
    @Override
    public TypeSpec getTypeSpec()
    {
        return typeSpec;
    }

    @Override
    public String toString()
    {
        return type.toString();
    }
}
