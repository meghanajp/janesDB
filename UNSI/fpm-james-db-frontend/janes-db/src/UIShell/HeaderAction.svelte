<script>
    /**
     * @event {null} open
     * @event {null} close
     */
  
    /** Set to `true` to open the panel */
    export let isOpen = false;
  
    /**
     * Specify the icon to render when the action panel is closed.
     * Defaults to `<Switcher size={20} />`
     * @type {typeof import("svelte").SvelteComponent}
     */
    export let icon = Switcher;
  
    /**
     * Specify the icon to render when the action panel is open.
     * Defaults to `<Close size={20} />`
     * @type {typeof import("svelte").SvelteComponent}
     */
    export let closeIcon = Close;
  
    /**
     * Specify the text
     * Alternatively, use the named slot "text" (e.g., <div slot="text">...</div>)
     * @type {string}
     */
    export let text = undefined;
  
    /** Obtain a reference to the button HTML element */
    export let ref = null;
  
    /**
     * Customize the panel transition (i.e., `transition:slide`).
     * Set to `false` to disable the transition
     * @type {false | import("svelte/transition").SlideParams}
     */
    export let transition = { duration: 200 };
  
    import { createEventDispatcher } from "svelte";
    import { slide } from "svelte/transition";
    import Close from "carbon-icons-svelte/lib//Close.svelte";
    import Switcher from "carbon-icons-svelte/lib/Switcher.svelte";
    import {widgetOpenMutex} from "../../store";
    const dispatch = createEventDispatcher();
    import { onMount, onDestroy} from 'svelte';
    import {
      Theme
    } from "carbon-components-svelte";
  
    let refPanel = null;
  
    let widgetOpenMutexUnsubscribe;
    onMount(()=> {
      widgetOpenMutexUnsubscribe = widgetOpenMutex.subscribe((value)=>{
        if(!value && isOpen)
        {
          isOpen = !isOpen;
          dispatch('close');
        }
      });
      });	
    onDestroy(()=> {
      widgetOpenMutexUnsubscribe();
      });
  
  </script>
  
  <!-- <svelte:window
    on:click="{({ target }) => {
      if (isOpen && !ref.contains(target) && !refPanel.contains(target)) {
        isOpen = false;
        dispatch('close');
      }
    }}"
  /> -->
      <button
        bind:this="{ref}"
        type="button"
        class:bx--header__action="{true}"
        class:bx--header__action--active="{isOpen}"
        class:action-text="{text}"
        {...$$restProps}
        on:click
        on:click|stopPropagation="{() => {
          if(!isOpen && $widgetOpenMutex){ //Closed and i am trying to open my widget.
            widgetOpenMutex.set(false);
          }
          isOpen = !isOpen;
          dispatch(isOpen ? 'open' : 'close');
          if(isOpen){
            widgetOpenMutex.set(true);
          }
        }}"
      >
        {#if isOpen}
          <slot name="closeIcon">
            <svelte:component this="{icon}" size="{30}" />
            <!-- <svelte:component this="{closeIcon}" size="{20}" style="border: 2px solid #0f62fe"/> -->
          </slot>
        {:else}
          <slot name="icon">
            <svelte:component this="{icon}" size="{20}" />
          </slot>
        {/if}
        <slot name="text">
          {#if text}<span>{text}</span>{/if}
        </slot>
      </button>
      {#if isOpen}
        <div
          bind:this="{refPanel}"
          class:bx--header-panel="{true}"
          class:bx--header-panel--expanded="{true}"
          transition:slide|local="{{
            ...transition,
            duration: transition === false ? 0 : transition.duration,
          }}"
        >
          <slot />
        </div>
      {/if}
  
  <style>
    /* 320px — 480px: Mobile devices
    481px — 768px: iPads, Tablets
    769px — 1024px: Small screens, laptops
    1025px — 1200px: Desktops, large screens
    1201px and more —  Extra large screens, TV */
    @media (max-width: 479px) {
      .bx--header-panel--expanded{
        width: 100%;
      }
    }
    @media (min-width: 481px) and (max-width: 768px)  {
      .bx--header-panel--expanded{
        width: 50%;
      }
    }
    @media (min-width: 769px) and (max-width: 1024px){
      .bx--header-panel--expanded{
        width: 370px;
      }
    }
  
    @media (min-width: 1024px) and (max-width: 1370px){
      .bx--header-panel--expanded{
        width: 370px;
      }
    }
    @media (min-width: 1370px) and (max-width: 1921px){
      .bx--header-panel--expanded{
        width: 370px;
      }
    }
    @media (min-width: 1370px){
      .bx--header-panel--expanded{
        width: 370px;
      }
    }

    :global(.bx--header-panel){
        z-index:900;
        margin-top:10px;
        background-color: rgba(0, 0, 0, 0.7);
    }
  
    :global(.white>svg){
      fill:#333;
    }
    :global(.white .bx--header__action--active){
      border: 1px solid #333;

    }
    :global(.g100 .bx--header__action--active){
      border: 1px solid white;
    }
    :global(.white:hover){
      background:rgb(185, 185, 185);
    }
    :global(.g100>svg){
      fill:white;
    }
    .action-text {
      display: inline-flex;
      align-items: center;
      width: auto;
  
      /** 2px bottom padding aligns icon with `HeaderAction` */
      padding: 0 1rem 2px 1rem;
  
      /** `body-short-01` styles */
      font-size: 0.875rem;
      line-height: 1.28572;
      letter-spacing: 0.16px;
  
      /** Same color as `Header` platformName */
      color: #f4f4f4;
    }
  
    .action-text > span {
      margin-left: 0.75rem;
    }
    
  </style>
  