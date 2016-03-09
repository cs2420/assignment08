package assignment08;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.Before;
import org.junit.Test;

public class Tests {
	BinarySearchTree<Integer> emptyTree;
	BinarySearchTree<Integer> smallTree;
	BinarySearchTree<String> mediumTree;
	BinarySearchTree<Integer> oneItem;

	
	@Before
	public void listSetUp() throws FileNotFoundException{
		emptyTree = new BinarySearchTree<Integer>();
		
		oneItem = new BinarySearchTree<Integer>();
		oneItem.add(0);
		
		smallTree = new BinarySearchTree<Integer>();
		smallTree.add(0);
		smallTree.add(1);
		smallTree.add(2);
		smallTree.add(3);
		
		mediumTree = new BinarySearchTree<String>();
		Scanner sc = new Scanner(new File("good_luck.txt"));
		sc.useDelimiter("\\s*[^a-zA-Z]\\s*");
		
		while(sc.hasNext()){
			String s = sc.next();
			if (!s.equals("")) {
				mediumTree.add(s.toLowerCase());
			}
		}
		sc.close();
	}

	@Test
	public void Add() {
		assertTrue(emptyTree.add(0));
		assertEquals(1, emptyTree.size());
		assertTrue(emptyTree.add(1));
		assertEquals(2, emptyTree.size());
		assertTrue(emptyTree.add(-1));
		assertEquals(3, emptyTree.size());
		
		//emptyTree.writeDot("dotfile");
	}
	
	@Test
	public void Contains() {
		assertTrue(smallTree.contains(2));
		assertTrue(smallTree.contains(0));
		assertTrue(smallTree.contains(3));
		assertFalse(smallTree.contains(5));
		assertFalse(smallTree.contains(-1));
		assertFalse(smallTree.contains(6));
		
		//smallTree.writeDot("dotfile");
	}
	
	@Test
	public void GetFirst() {
		//mediumTree.writeDot("dotfile");
		assertEquals("and", mediumTree.first());
		assertEquals((Integer)0, smallTree.first());
			
	}
	
	@Test
	public void GetLast() {
		assertEquals("which", mediumTree.last());
		assertEquals((Integer)3, smallTree.last());
			
	}
	
	@Test
	public void removeLeaf() {
		assertTrue(mediumTree.remove("searches"));
		//mediumTree.writeDot("dotfile");
		
		assertTrue(oneItem.remove(0));
		//oneItem.writeDot("dotfile");
			
	} 
	@Test
	public void nodeWithOneChild() {
		mediumTree.writeDot("original");
		assertTrue(mediumTree.remove("bst"));
		mediumTree.writeDot("dotfile");
	
		
		//assertTrue(oneItem.remove(0));
		//oneItem.writeDot("dotfile");
			
	} 
	
	
}
