// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode 

package net.minecraft.src;

import java.util.Random;

// Referenced classes of package net.minecraft.src:
//            Block, Material, IBlockAccess, WorldChunkManager, 
//            ColorizerGrass, World

public class BlockGrass extends Block
{

    protected BlockGrass(int i)
    {
        super(i, Material.grassMaterial);
        blockIndexInTexture = 3;
        setTickOnLoad(true);
    }

    public int getBlockTexture(IBlockAccess iblockaccess, int i, int j, int k, int l)
    {
    	int l1 = iblockaccess.getBlockMetadata(i, j, k);
    	if(l1 == 0)
    	{
            if(l == 1)
            {
                return 0;
            }
            if(l == 0)
            {
                return 2;
            }
    	}else
    	{
    		if(l == 1)
    		{
    			return 38;
    		}
    		if(l == 0)
    		{
    			return 7+l1;
    		}
    	}
        Material material = iblockaccess.getBlockMaterial(i, j + 1, k);
        if(l1 == 0)
        {
        	return material != Material.snow && material != Material.builtSnow ? 3 : 68;
        }else
        {
        	return material != Material.snow && material != Material.builtSnow ? 23 + l1 : 55 + l1;
        }
    }

    public int colorMultiplier(IBlockAccess iblockaccess, int i, int j, int k)
    {
        iblockaccess.getWorldChunkManager().func_4069_a(i, k, 1, 1);
        double d = iblockaccess.getWorldChunkManager().temperature[0];
        double d1 = iblockaccess.getWorldChunkManager().humidity[0];
        return ColorizerGrass.getGrassColor(d, d1);
    }

    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if(world.multiplayerWorld)
        {
            return;
        }
        if(world.getBlockLightValue(i, j + 1, k) < 4 && Block.lightOpacity[world.getBlockId(i, j + 1, k)] > 2)
        {
            if(random.nextInt(4) != 0)
            {
                return;
            }
            world.setBlockWithNotify(i, j, k, Block.dirt.blockID);
        } else
        if(world.getBlockLightValue(i, j + 1, k) >= 9)
        {
            int l = (i + random.nextInt(3)) - 1;
            int i1 = (j + random.nextInt(5)) - 3;
            int j1 = (k + random.nextInt(3)) - 1;
            int k1 = world.getBlockId(l, i1 + 1, j1);
            if(world.getBlockId(l, i1, j1) == Block.dirt.blockID && world.getBlockLightValue(l, i1 + 1, j1) >= 4 && Block.lightOpacity[k1] <= 2)
            {
                world.setBlockWithNotify(l, i1, j1, Block.grass.blockID);
            }
        }
    }

    public int idDropped(int i, Random random)
    {
        return Block.dirt.idDropped(0, random);
    }
    
    public int getRenderPass(World world, int i, int j)
    {
    	if(j > 0 && j < 4)
    	{
    		return 2;
    	}else
    	{
    		return 0;
    	}
    }
    
    protected int damageDropped(int i)
    {
        return i < 4 ? i : 0;
    }
    
    public boolean isBlockSideSnowy(IBlockAccess blockAccess, int i, int j, int k)
    {
    	int b = getBlockTexture(blockAccess, i, j, k, 2);
    	return b == 68 || (b > 55 && b < 59);
    }
    
    public boolean isBlockSideGrass(IBlockAccess blockAccess, int i, int j, int k)
    {
    	int b = getBlockTexture(blockAccess, i, j, k, 2);
    	return b == 3 || (b > 23 && b < 27);
    }
}
