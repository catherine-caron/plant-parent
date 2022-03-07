-- Code your testbench here
library IEEE;
use IEEE.std_logic_1164.all;

entity tb_state is
end;

architecture tb of tb_state is
	signal clk: std_logic;
    signal reset: std_logic;
    signal input: std_logic_vector(7 downto 0);
    signal output: std_logic;
    
    component comments_fsm
    port (clk : in std_logic;
      reset : in std_logic;
      input : in std_logic_vector(7 downto 0);
      output : out std_logic
  );
  	end component;
    
constant SLASH_CHARACTER : std_logic_vector(7 downto 0) := "00101111";
constant STAR_CHARACTER : std_logic_vector(7 downto 0) := "00101010";
constant NEW_LINE_CHARACTER : std_logic_vector(7 downto 0) := "00001010";
constant A_CHARACTER : std_logic_vector(7 downto 0) := "01000001";

begin
	DUT: comments_fsm port map (clk, reset, input, output);
    
    clk_process: process
    begin
    	clk <= '0', '1' after 5 ns;
        wait for 10 ns;
    end process;

-- \n: 00001010
-- /: 00101111
-- *: 00101010
    
    
    test_process: process
    begin
    	
        -- No comment test
    	-- This test will produce an output of 0-0-0, as there are no comments in this string
    	reset <= '1';
        wait for 10 ns;
        reset <= '0';
       
        input <= A_CHARACTER;
        wait for 10 ns;
        input <= A_CHARACTER;
        wait for 10 ns;
        input <= A_CHARACTER;
        wait for 10 ns;
    
    -- Just multiline comment test 
    -- This test will produce an output of 0-1-1-1-0. First two chars not comment, last 3 are comment
    	reset <= '1';
        wait for 10 ns;
        reset <= '0';
       
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= A_CHARACTER;
        wait for 10 ns;
		input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
    
    -- Just single line comment test 
    -- This test will produce an output of 0–1-1-0. First two chars are not comments, last 2 are comment
    	reset <= '1';
        wait for 10 ns;
        reset <= '0';
       
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        input <= A_CHARACTER;
        wait for 10 ns;
		input <= NEW_LINE_CHARACTER;
        wait for 10 ns;
        
    -- New line within multiline comment test 
    -- This test will produce an output of 0-1-1-1-1-0. This is becausd a new line character within a multiline comment should not terminate the comment.
    	reset <= '1';
        wait for 10 ns;
        reset <= '0';
       
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= A_CHARACTER;
        wait for 10 ns;
		input <= NEW_LINE_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        
    -- Non comment + multiline comment
    -- This test will produce an output of 0-0-1-1-1-0. This is because the first 3 chars not comment and the last 3 are comment
    	reset <= '1';
        wait for 10 ns;
        reset <= '0';
       
        input <= A_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
		input <= A_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
    
    -- Non comment + single line comment
    -- This test will produce an output of 0-0–1-1-0. This is because the first 3 chars not comment and the last 2 are comment
    	reset <= '1';
        wait for 10 ns;
        reset <= '0';
       
        input <= A_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
		input <= A_CHARACTER;
        wait for 10 ns;
        input <= NEW_LINE_CHARACTER;
        wait for 10 ns;
            
    -- Double star opening and closing test
    -- This test will produce an output of 0–1-1-1-1-1-0 
    	reset <= '1';
        wait for 10 ns;
        reset <= '0';
       
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
		input <= A_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= STAR_CHARACTER;
        wait for 10 ns;
        input <= SLASH_CHARACTER;
        wait for 10 ns;
        
    end process;
end tb;